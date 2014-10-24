'use strict';

covoitApp.factory('daoEvents',  ['$http', function($http){
	
	var REST_API_ROOT_URL = "/covoiturage/rest";

	var getAllEvents = function(callback) {
		$http.get(REST_API_ROOT_URL + '/events').success(callback);
	};

	return {
		"getAllEvents": getAllEvents
	};

}]);