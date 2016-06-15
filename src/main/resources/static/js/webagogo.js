angular.module('webagogo', [])
  .controller('webagogoCtrl', function($scope, $http) {

	$http.get('/placePiece/').then(function(response) {
		console.log("Got data");
		console.log(response.data);
	})
});