'use strict';

covoitApp.factory('daoEvents',  ['$http', function($http){
	
	var REST_API_ROOT_URL = "http://localhost:8080/covoiturage/rest";

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
	
	var addEvent = function(date, place, desc, callback) {
		$http.post(REST_API_ROOT_URL + '/events',
				  encodeURI("date="+date) + "&" + encodeURI("place="+place) + "&" + encodeURI("desc="+desc),
				  {headers: {'Content-Type': 'application/x-www-form-urlencoded', 'Accept': 'application/x-www-form-urlencoded'}}).success(callback);
	};
	
	var addUserAsPassenger = function(idEvent, username, okcallback, noseatscallback) {
		$http.post(REST_API_ROOT_URL + '/events/'+idEvent+'/join',
				  encodeURI("username="+username),
				  {headers: {'Content-Type': 'application/x-www-form-urlencoded', 'Accept': 'application/x-www-form-urlencoded'}})
				  .success(function(data, status) {
					  if (status == 200) {
						  return okcallback();
					  } else if (status == 202) {
						  return noseatscallback();
					  }
				  });
	};
	
	var addUserAsDriver = function(idEvent, username, modelcar, nbseatscar, okcallback, noseatscallback) {
		$http.post(REST_API_ROOT_URL + '/events/'+idEvent+'/joindriver',
				  encodeURI("username="+username) + "&" + encodeURI("modelcar="+modelcar) + "&" + encodeURI("nbseatscar="+nbseatscar),
				  {headers: {'Content-Type': 'application/x-www-form-urlencoded', 'Accept': 'application/x-www-form-urlencoded'}})
				  .success(function(data, status) {
					  if (status == 200) {
						  return okcallback();
					  } else if (status == 202) {
						  return noseatscallback();
					  }
				  });
	};

	return {
		"getAllEvents": getAllEvents,
		"getEventUsers": getEventUsers,
		"getEventCars": getEventCars,
		"deleteUserFromEvent": deleteUserFromEvent,
		"addEvent": addEvent,
		"addUserAsPassenger": addUserAsPassenger,
		"addUserAsDriver": addUserAsDriver
	};

}]);
