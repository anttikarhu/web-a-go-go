angular.module('webagogo', []).constant('_', window._).controller(
		'webagogoCtrl',
		function($scope, $http) {
			// Get canvas
			var canvas = document.getElementById('boardCanvas');
			var context = canvas.getContext('2d');

			// Size of the draw area
			var canvasSize = 600;
			// Margin for each side where the board begins
			var boardMargin = 40;
			// Default board size in positions
			var defaultBoardSize = 19;
			// Stone marker radius
			var markerRadius = 4;

			// Size of the board grid
			var gridSize = canvasSize - 2 * boardMargin;
			// Board size in positions
			var boardSize = defaultBoardSize;
			// Space between lines
			var lineSpacing = (canvasSize - boardMargin * 2) / (boardSize - 1);

			// Set canvas size
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

			function drawGame() {
				// Draw board
				drawBoard();

				// Draw stones
				// TODO
			}

			function drawMarker(posX, posY) {
				var canvasX = boardMargin + posX * lineSpacing;
				var canvasY = boardMargin + posY * lineSpacing;

				context.beginPath();
				context.arc(canvasX, canvasY, markerRadius, 0, 2 * Math.PI, false);
				context.fillStyle = 'gray';
				context.fill();
				context.lineWidth = 1;
				context.strokeStyle = 'black';
				context.stroke();
			}

			function newGame() {
				$scope.game = null;

				$http.get('/newGame/').then(function(response) {
					$scope.game = response.data;
					drawBoard();
				});
			}
			$scope.newGame = newGame;

			function translateMouseEventToPosCoords(event) {
				// Translate canvas coordinates to "board coordinates"
				var boardX = _.clamp(event.offsetX - boardMargin, 0, gridSize);
				var boardY = _.clamp(event.offsetY - boardMargin, 0, gridSize);

				// Determine which position is nearest to the coordinate
				var posX = Math.round(boardX / lineSpacing);
				var posY = Math.round(boardY / lineSpacing);

				return {
					x : posX,
					y : posY
				};
			}

			$scope.boardMouseMove = function(event) {
				var pos = translateMouseEventToPosCoords(event);

				// Draw a marker to the position so user knows where stone will
				// be placed
				drawGame();
				drawMarker(pos.x, pos.y);
			};

			$scope.boardMouseLeave = function(event) {
				// Refresh board when mouse leaves canvas
				drawGame();
			};

			$scope.boardClicked = function(event) {
				var pos = translateMouseEventToPosCoords(event);
				// TODO Place stone to board
				console.log("Clicked at position " + pos.x + ", " + pos.y);
			};

			// Ready start
			drawBoard();
			newGame();
	});