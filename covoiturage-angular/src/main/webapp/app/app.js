'use strict';

var covoitApp = angular.module('covoitApp', ['ui.router', 'ngDialog', 'ngQuickDate', function ($httpProvider) {
	
}]);

covoitApp.config(function($stateProvider, $urlRouterProvider, $httpProvider) {
	
	// For any unmatched url, redirect to /events
	$urlRouterProvider.otherwise("/events");

	// Now set up the states
	$stateProvider.state('events', {
		url: "/events",
		templateUrl: "view_events/view_events.html"
	}).state('events.item', {
		url: "/{eventId}",
		templateUrl: "view_event/view_event.html"
	});
});