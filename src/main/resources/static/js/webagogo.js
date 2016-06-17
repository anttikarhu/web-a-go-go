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
			// Stone radius
			var stoneRadius = 12;
			// Line color
			var lineColor = "black";
			// Board background color
			var boardColor = "antiqueWhite";
			// Board rim color
			var boardRimColor = "sienna";

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
				context.fillStyle = boardRimColor;
				context.fillRect(0, 0, canvas.width, canvas.height);
				
				// Draw board
				context.fillStyle = boardColor;
				context.fillRect(boardMargin, boardMargin, canvas.width - 2
						* boardMargin, canvas.height - 2 * boardMargin);

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
					context.strokeStyle = lineColor;
					context.stroke();
				}

				// Draw horizontal lines
				for (var y = 0; y < boardSize; y++) {
					context.beginPath();
					context.moveTo(boardMargin, Math.round(boardMargin
							+ lineSpacing * y));
					context.lineTo(canvasSize - boardMargin, Math
							.round(boardMargin + lineSpacing * y));
					context.strokeStyle = lineColor;
					context.stroke();
				}
			}

			function translatePosCoordsToCanvasCoords(pos) {
				var canvasX = boardMargin + pos.x * lineSpacing;
				var canvasY = boardMargin + pos.y * lineSpacing
				return {
					x : canvasX,
					y : canvasY
				};
			}

			function drawStone(pos, color) {
				var canvasPos = translatePosCoordsToCanvasCoords(pos);
				var fillColor = "black";
				if (color === "WHITE") {
					fillColor = "white";
				}

				// Shadow
				context.save();
				context.beginPath();
				context.arc(canvasPos.x, canvasPos.y, stoneRadius, 0,
						2 * Math.PI, false);
				context.fillStyle = fillColor;
				context.shadowColor = 'rgba(0, 0, 0, 0.2)';
				context.shadowBlur = 10;
				context.shadowOffsetX = 3;
				context.shadowOffsetY = 3;
				context.fill();
				context.restore();

				// Piece
				context.save();
				context.beginPath();
				context.arc(canvasPos.x, canvasPos.y, stoneRadius, 0,
						2 * Math.PI, false);
				context.fillStyle = fillColor;
				context.fill();
				context.lineWidth = 1;
				context.strokeStyle = 'darkGray';
				context.stroke();
				context.restore();
			}

			function drawGame() {
				// Draw board
				drawBoard();

				if ($scope.game) {
					// Draw stones
					for (var y = 0; y < $scope.game.board.length; y++) {
						for (var x = 0; x < $scope.game.board.length; x++) {
							if ($scope.game.board[y][x] === "BLACK"
									|| $scope.game.board[y][x] === "WHITE") {
								drawStone({
									x : x,
									y : y
								}, $scope.game.board[y][x]);
							}
						}
					}
				}
			}

			function drawMarker(pos) {
				var canvasPos = translatePosCoordsToCanvasCoords(pos);

				context.beginPath();
				context.arc(canvasPos.x, canvasPos.y, markerRadius, 0,
						2 * Math.PI, false);
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
					boardSize = $scope.game.board.length;
					drawBoard();
				});
			}
			$scope.newGame = newGame;

			function translateMouseEventToPosCoords(event) {
				// Translate canvas coordinates to "board coordinates"
				var boardX = _.clamp(event.offsetX - boardMargin, 0, gridSize);
				var boardY = _.clamp(event.offsetY - boardMargin, 0, gridSize);

				// Determine which position is nearest to the coordinate
				var posX = _.clamp(Math.round(boardX / lineSpacing), 0,
						boardSize - 1);
				var posY = _.clamp(Math.round(boardY / lineSpacing), 0,
						boardSize - 1);

				return {
					x : posX,
					y : posY
				};
			}

			$scope.boardMouseMove = function(event) {
				// Draw a marker to the position so user knows where stone will
				// be placed
				drawGame();
				var pos = translateMouseEventToPosCoords(event);
				if ($scope.game && $scope.game.board[pos.y][pos.x] === "FREE") {
					drawMarker(pos);
				}
			};

			$scope.boardMouseLeave = function(event) {
				// Refresh board when mouse leaves canvas so the marker does
				// not linger there
				drawGame();
			};

			$scope.boardClicked = function(event) {
				// Stone is first placed to board, but it may be removed if
				// server thinks it was not a proper move
				var pos = translateMouseEventToPosCoords(event);
				if ($scope.game && $scope.game.board[pos.y][pos.x] === "FREE") {
					$scope.game.board[pos.y][pos.x] = $scope.game.playersTurn;
					drawGame();

					if ($scope.game.playersTurn === "BLACK") {
						$scope.game.playersTurn = "WHITE";
					} else {
						$scope.game.playersTurn = "BLACK";
					}
			
					// Send message to server
					$http.get('/makeMove/', {
						params : {
							moveType: "PLACE_STONE",
							x: pos.x,
							y: pos.y,
							gameId: $scope.game.gameId
						}
					}).then(function(response) {
						$scope.game = response.data;
					});
				}
			};

			// Ready start
			drawBoard();
			newGame();
	});