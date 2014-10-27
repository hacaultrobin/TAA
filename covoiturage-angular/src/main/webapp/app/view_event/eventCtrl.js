'use strict';

covoitApp.controller('eventCtrl', ['$scope', '$stateParams', '$state', 'daoEvents', 'ngDialog',
                                   function($scope, $stateParams, $state, daoEvents, ngDialog) {
	
	/* DEFINITION OF THE CALLBACKS */
	var getEventUsersCallback = function(data) {
		$scope.event.users = data;
	};
	var getEventCarsCallback = function(data) {
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
				daoEvents.getEventUsers($scope.event.id, getEventUsersCallback);
				daoEvents.getEventCars($scope.event.id, getEventCarsCallback);
			}
		}
	});
	
	/* Tells if it is possible to delete the user, ie no-driver or driver with no passengers in the car */
	$scope.isDeleteUserAvailable = function(user) {
		if (!user.driver) {
			return true;
		} else if ($scope.event.cars != undefined && $scope.event.cars != []) {
			var userCar = getCar(user.carId, $scope.event.cars);
			return userCar.nbAvailableSeat == userCar.nbSeat - 1;
		}
		return false;
	};
	
	$scope.clickDeleteUserButton = function(user) {
		daoEvents.deleteUserFromEvent($scope.event.id, user.id, function() {
			daoEvents.getEventUsers($scope.event.id, getEventUsersCallback);
			daoEvents.getEventCars($scope.event.id, getEventCarsCallback);
		});
	};
	
	$scope.clickAddPassengerButton = function () {
		ngDialog.open({template: 'view_event/addPassenger_dialog.html', scope: $scope});
	};
	
	$scope.clickAddDriverButton = function () {
		ngDialog.open({template: 'view_event/addDriver_dialog.html', scope: $scope});
	};
	
	$scope.validateAddPassenger = function (name) {
		if (name == undefined || name == "") {
			alert("Entrez un nom de passager correct");
		} else {
			ngDialog.close();
			alert("TODO : Ajouter le nouveau passager " + name);
		}
	};
	
	$scope.validateAddDriver = function (driverForm) {
		if (driverForm != undefined) {
			if (driverForm.name == undefined || driverForm.name == "") {
				alert("Entrez un nom de conducteur correct");
			} else if (driverForm.carModel == undefined || driverForm.carModel == "") {
				alert("Entrez un modele de voiture correct");
			} else if (driverForm.carSeats == undefined || driverForm.carSeats < 1) {
				alert("Entrez un nombre de sieges correct");
			} else {
				ngDialog.close();
				alert("TODO : Ajouter le nouveau passager " + driverForm.name);
			}			
		}
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