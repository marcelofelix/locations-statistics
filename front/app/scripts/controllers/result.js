'use strict';

var app = angular.module('locationApp');

app.controller('ResultCtrl', function($scope, result) {

	$scope.result = result;
	$scope.colDefs = [
	{
		field:'value',
		displayName:'Valor'
	},
	{
		field:'percent',
		displayName:'Porcentagem'
	}
	];
});