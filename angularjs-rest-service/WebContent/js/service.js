var app = angular.module('serviceDemoApp', [ 'ngResource' ]);

app.factory('DemoService', function($resource) {

	return $resource('/eMuseo-webapp/api/exhibits', {}, {

		query : {

			method : 'GET',

			params : {},
			isArray : true
		}
	});
});
