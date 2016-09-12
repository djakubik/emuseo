var app = angular.module('exhibitApp', [ 'ngResource', 'angularGrid' ]);

app.factory('ExhibitRestService', function($resource) {

	return $resource('/app/api/exhibits', {}, {

		query : {

			method : 'GET',

			params : {},
			isArray : true
		}
	});
});
