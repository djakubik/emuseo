/*******************************************************************************
 * Copyright (c) 2016 Darian Jakubik.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Darian Jakubik - initial API and implementation
 ******************************************************************************/
package me.uni.emuseo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class StorageUtils {

	public static final int TEMP_BUFFER_SIZE = 16 * 1024;

	public static FileInputStream readFileAsStream(File file) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		return fileInputStream;
	}

	public static FileChannel readFileAsChannel(File file) throws IOException {
		@SuppressWarnings("resource")
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		FileChannel inChannel = raf.getChannel();
		return inChannel;
	}

	public static byte[] loadFileWithStream(File file) throws IOException {
		FileInputStream fis = null;
		try {
			// PRE LOADING
			fis = readFileAsStream(file);
			int size = (int) file.length();

			// LOADING WITH STREAM
			byte[] bytes = new byte[size];
			fis.read(bytes, 0, size);
			return bytes;
		} finally {
			closeStream(fis);
		}
	}

	public static byte[] loadFileWithChannel(File file) throws IOException {
		FileChannel inChannel = null;
		try {
			// PRE LOADING
			inChannel = readFileAsChannel(file);
			int size = (int) inChannel.size();

			// LOADING WITH CHANNEL AND BYTEBUFFER
			byte[] bytes = new byte[size];
			ByteBuffer bb = ByteBuffer.wrap(bytes);
			inChannel.read(bb);

			return bytes;
		} finally {
			closeStream(inChannel);
		}
	}

	public static byte[] loadFileWithMappedByteBuffer(File file) throws IOException {
		FileChannel inChannel = null;
		try {
			// PRE LOADING
			inChannel = readFileAsChannel(file);
			int size = (int) inChannel.size();

			// LOADING WITH MAPPEDBYTEBUFFER
			MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);
			buffer.load();
			byte[] bytes = new byte[size];
			buffer.get(bytes);

			return bytes;
		} finally {
			closeStream(inChannel);
		}
	}

	public static long saveFileWithStream(File targetFile, byte[] bytes) throws IOException {
		return saveFileWithStream(targetFile, new ByteArrayInputStream(bytes));
	}

	public static long saveFileWithStream(File targetFile, InputStream inputStream) throws IOException {
		final FileOutputStream fileOutputStream;
		try {
			if (targetFile == null) {
				throw new IOException("fileNotFound");
			}
			fileOutputStream = new FileOutputStream(targetFile);
		} catch (FileNotFoundException e1) {
			throw e1;
		}

		try {
			// SAVING WITH STREAM
			long size = copyStream(inputStream, fileOutputStream);
			return size;
		} catch (IOException e) {
			// DELETE
			closeStream(fileOutputStream);
			targetFile.delete();
			throw e;
		} finally {
			closeStream(inputStream);
			closeStream(fileOutputStream);
		}
	}

	public static long saveFileWithChannel(File targetFile, byte[] bytes) throws IOException {

		final RandomAccessFile raf;
		final WritableByteChannel outputChannel;
		try {
			if (targetFile == null) {
				throw new IOException("fileNotFound");
			}
			// PRE SAVING
			raf = new RandomAccessFile(targetFile, "rw");
			raf.setLength(0);
			outputChannel = raf.getChannel();
		} catch (IOException e) {
			throw e;
		}
		try {
			// SAVING WITH CHANNEL AND BYTEBUFFER
			outputChannel.write(ByteBuffer.wrap(bytes));
			return bytes.length;
		} catch (IOException e2) {
			closeStream(outputChannel);
			targetFile.delete();
			throw e2;
		} finally {
			closeStream(outputChannel);
			closeStream(raf);
		}
	}

	/**
	 * Interruptible
	 * 
	 * @param targetFile
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static long saveFileWithChannel(File targetFile, InputStream inputStream) throws IOException {

		final RandomAccessFile raf;
		final WritableByteChannel outputChannel;
		final ReadableByteChannel inputChannel;
		try {
			if (targetFile == null) {
				throw new IOException("fileNotFound");
			}
			// PRE SAVING
			raf = new RandomAccessFile(targetFile, "rw");
			raf.setLength(0);
			outputChannel = raf.getChannel();
			inputChannel = Channels.newChannel(inputStream);
		} catch (IOException e) {
			throw e;
		}
		try {
			// SAVING WITH CHANNEL
			long size = copyChannel(inputChannel, outputChannel);
			return size;
		} catch (IOException e2) {
			closeStream(outputChannel);
			targetFile.delete();
			throw e2;
		} finally {
			closeStream(outputChannel);
			closeStream(raf);
		}
	}

	public static long saveFileWithMappedBuffer(File targetFile, byte[] bytes) throws IOException {

		final RandomAccessFile raf;
		final FileChannel outputChannel;
		try {
			if (targetFile == null) {
				throw new IOException("fileNotFound");
			}
			// PRE SAVING
			raf = new RandomAccessFile(targetFile, "rw");
			raf.setLength(0);
			outputChannel = raf.getChannel();
		} catch (IOException e) {
			throw e;
		}
		try {
			// SAVING WITH MAPPEDBYTEBUFFER
			MappedByteBuffer buf = outputChannel.map(FileChannel.MapMode.READ_WRITE, 0, bytes.length);
			buf.put(bytes);
			buf.force();
			return bytes.length;
		} catch (IOException e2) {
			closeStream(outputChannel);
			targetFile.delete();
			throw e2;
		} finally {
			closeStream(outputChannel);
			closeStream(raf);
		}
	}

	public static long saveFileWithMappedBuffer(File targetFile, InputStream inputStream) throws IOException {
		return saveFileWithMappedBuffer(targetFile, toByteArray(inputStream));
	}

	public static long copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
		byte[] buffer = new byte[TEMP_BUFFER_SIZE];
		long size = 0L;
		int n = 0;
		while (-1 != (n = inputStream.read(buffer))) {
			outputStream.write(buffer, 0, n);
			size += n;
		}
		return size;
	}

	/**
	 * Interruptible
	 * 
	 * @param src
	 * @param dest
	 * @return
	 * @throws IOException
	 */
	public static long copyChannel(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {
		final ByteBuffer buffer = ByteBuffer.allocateDirect(TEMP_BUFFER_SIZE);
		long size = 0L;
		int n = 0;
		while (-1 != (n = src.read(buffer))) {
			buffer.flip();
			dest.write(buffer);
			buffer.compact();
			size += n;
		}
		buffer.flip();
		while (buffer.hasRemaining()) {
			size += dest.write(buffer);
		}
		return size;
	}

	public static byte[] toByteArray(InputStream inputStream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int n;
		byte[] data = new byte[TEMP_BUFFER_SIZE];

		while (-1 != (n = inputStream.read(data, 0, data.length))) {
			buffer.write(data, 0, n);
		}
		buffer.flush();
		return buffer.toByteArray();
	}

	public static void closeStream(Closeable s) {
		if (s != null) {
			try {
				s.close();
			} catch (IOException e) {
			}
		}
	}

	public static void createDirIfNotExists(File directory) throws IOException {
		if (!directory.exists()) {
			try {
				boolean madeDirs = directory.mkdirs();
				if (!madeDirs) {
					throw new IOException("Couldn't create directories on the path: " + directory.getAbsolutePath());
				}
			} catch (SecurityException secEx) {
				throw new IOException("Couldn't create directories on the path: " + directory.getAbsolutePath()
						+ ", nested exception: " + secEx.getMessage());
			}
		}
	}

	/**
	 * Removes file from the disk.
	 * 
	 * @param file
	 *            File on disk to remove.
	 * @return True if deleting file was successful, false otherwise.
	 */
	public static boolean deleteFile(File file) {
		if (file == null || !file.exists()) {
			return false;
			// do not throw anything, because results are satisfying
		}
		if (file.isFile()) {
			return file.delete();
		} else {
			return false;
			// throw new IOException("File happen to be a directory");
		}
	}

}
