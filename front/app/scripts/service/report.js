'use strict';

var app = angular.module('locationApp');

app.factory('ReportService', function($http, $q) {
	return {
		loadReportResult: function(id) {
			return $http.get('report/' + id + '/result')
				.then(function(result) {
					return result.data;
				}, function(error) {
					return $q.reject(error);
				});
		},

		loadReports: function() {
			return $http.get('report')
				.then(function(result) {
					return result.data;
				}, function(error) {
					return $q.reject(error);
				});
		},

		schedule: function() {
			return $http.post('report/schedule')
				.then({}, function(error) {
					return $q.reject(error);
				});
		}
	};
});