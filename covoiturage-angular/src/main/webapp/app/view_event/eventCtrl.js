'use strict';

covoitApp.controller('eventCtrl', ['$scope', '$stateParams', '$state', 'daoEvents', 'ngDialog',
                                   function($scope, $stateParams, $state, daoEvents, ngDialog) {
	
	/* DEFINITION OF THE CALLBACKS */
	$scope.getEventUsersCallback = function(data) {
		$scope.event.users = data;
	};
	$scope.getEventCarsCallback = function(data) {
		$scope.event.cars = data;
	};

	/* Observe the loadEventsOk value from the parent controller */
	$scope.$watch("loadEventsOk", function(loadEventsOk) {
		if (loadEventsOk) {
			var current_event = getEvent(parseInt($stateParams.eventId), $scope.events);
			if (current_event == null) {
				$state.go("events");
			} else {
				$scope.event = current_event;
				$scope.setIdSelectedEvent(current_event.id);
				daoEvents.getEventUsers($scope.event.id, $scope.getEventUsersCallback);
				daoEvents.getEventCars($scope.event.id, $scope.getEventCarsCallback);
			}
		}
	});
	
	/* Tells if it is possible to delete the user, ie no-driver or driver with no passengers in the car */
	$scope.isDeleteUserAvailable = function(user) {
		if (!user.driver) {
			return true;
		} else if ($scope.event.cars != undefined && $scope.event.cars != []) {
			var userCar = getCar(user.carId, $scope.event.cars);
			return userCar == null || userCar.nbAvailableSeat == userCar.nbSeat - 1;
		}
		return false;
	};
	
	/* Tells if seats are avalailable to join the event as a passenger */
	$scope.isSeatsAvailable = function () {
		if ($scope.event != undefined && $scope.event.cars != undefined) {
			for (var i = 0; i < $scope.event.cars.length; i++) {				
				if ($scope.event.cars[i].nbAvailableSeat > 0) return true;
			}
		}
		return false;
	};
	
	$scope.clickDeleteUserButton = function(user) {
		daoEvents.deleteUserFromEvent($scope.event.id, user.id, function() {
			daoEvents.getEventUsers($scope.event.id, $scope.getEventUsersCallback);
			daoEvents.getEventCars($scope.event.id, $scope.getEventCarsCallback);
		});
	};

	/* Retrieve the event with id eventId from a list of events */
	var getEvent = function(eventId, events)  {
		if (isIndexOk(eventId)) {
			for (var i = 0; i < events.length; i++) {
				if (events[i].id == eventId) {
					return events[i];
				}
			}
		}
		return null;
	}

	/* Check if an index is OK to represent an event (ie, Natural number) */
	var isIndexOk = function(index) {
		if (typeof index === 'number') {
			if (index % 1 === 0 && index >= 0) {
				return true;
			}
		}
		return false;
	}
	
	/* Retrieve the car with id carId from a list of cars */
	var getCar = function(carId, cars) {
		for (var i = 0; i < cars.length; i++) {
			if (cars[i].id == carId) {
				return cars[i];
			}
		}
		return null;
	}

}]);