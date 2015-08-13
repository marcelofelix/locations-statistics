'use strict';

var app = angular.module('locationApp');

app.factory('ReportService', function($http, $q) {
	return {
		loadReportResult: function(id) {
			return $http.get('report/' + id + '/result')
				.then(function(result) {
					return result.data;
				}, function(error) {
					return $q.reject(error.data);
				});
		},

		loadReports: function() {
			return $http.get('report')
				.then(function(result) {
					return result.data;
				}, function(error) {
					return $q.reject(error.data);
				});
		},

		schedule: function() {
			return $http.post('report/schedule')
				.then({}, function(error) {
					return $q.reject(error.data);
				});
		},

		process: function(id) {
			return $http.post('report/' + id + '/process')
				.then({}, function(error) {
					return $q.reject(error.data);
				});
		}
	};
});