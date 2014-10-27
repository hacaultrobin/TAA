'use strict';

covoitApp.controller('eventsCtrl', ['$scope', 'daoEvents', function($scope, daoEvents) {
	
	$scope.loadEventsOk = false;

	/* Gets the list of events using the service daoEvents */
	daoEvents.getAllEvents(function (data) {
		for (var i = 0; i < data.length; i++) {
			var d = new Date(data[i].date);
			data[i].date = d;
		}		
		$scope.events = data;
		$scope.loadEventsOk = true;
	});

}]);