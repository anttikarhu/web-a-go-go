angular.module('webagogo', []).controller(
		'webagogoCtrl',
		function($scope, $http) {
			var canvas = document.getElementById('boardCanvas');
			var context = canvas.getContext('2d');

			var canvasSize = 600;
			var boardMargin = 40;
			var defaultBoardSize = 19;

			var boardSize = defaultBoardSize;
			var lineSpacing = (canvasSize - boardMargin * 2) / (boardSize - 1);

			canvas.width = canvasSize;
			canvas.height = canvasSize;

			function drawBoard() {
				// Clear canvas
				context.clearRect(0, 0, canvas.width, canvas.height);

				// Draw vertical lines
				for (var x = 0; x < boardSize; x++) {
					context.beginPath();
					// The coordinates are round so the lines will appear to be
					// same width on the canvas (only rounding those that can be
					// floats)
					context.moveTo(Math.round(boardMargin + lineSpacing * x),
							boardMargin);
					context.lineTo(Math.round(boardMargin + lineSpacing * x),
							canvasSize - boardMargin);
					context.stroke();
				}

				// Draw horizontal lines
				for (var y = 0; y < boardSize; y++) {
					context.beginPath();
					context.moveTo(boardMargin, Math.round(boardMargin
							+ lineSpacing * y));
					context.lineTo(canvasSize - boardMargin, Math
							.round(boardMargin + lineSpacing * y));
					context.stroke();
				}
			}

			function newGame() {
				$scope.game = null;

				$http.get('/newGame/').then(function(response) {
					$scope.game = response.data;
					drawBoard();
				});
			}

			$scope.newGame = newGame;

			drawBoard();
			newGame();
		});