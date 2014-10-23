'use strict';

covoitApp.controller('eventCtrl', ['$scope', '$stateParams', function($scope, $stateParams) {
	// We import the $stateParams service, to get parameters from the router
	
	// Controlleur fils de eventsCtrl --> Accès au scope de eventsCtrl
	
	$scope.eventId = $stateParams.eventId;
  
}]);