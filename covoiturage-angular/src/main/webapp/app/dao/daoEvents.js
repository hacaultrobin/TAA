'use strict';

covoitApp.factory('daoEvents',  ['$http', function($http){
	
	var REST_API_ROOT_URL = "/covoiturage/rest";

	var getAllEvents = function(callback) {
		$http.get(REST_API_ROOT_URL + '/events').success(callback);
	};
	
	var getEventUsers = function(idEvent, callback) {
		$http.get(REST_API_ROOT_URL + '/events/'+idEvent+'/users').success(callback);
	};
	
	var getEventCars = function(idEvent, callback) {
		$http.get(REST_API_ROOT_URL + '/events/'+idEvent+'/cars').success(callback);
	};
	
	var deleteUserFromEvent = function(idEvent, idUser, callback) {
		$http.delete(REST_API_ROOT_URL + '/events/'+idEvent+'/removeuser/'+idUser).success(callback);
	};

	return {
		"getAllEvents": getAllEvents,
		"getEventUsers": getEventUsers,
		"getEventCars": getEventCars,
		"deleteUserFromEvent": deleteUserFromEvent
	};

}]);