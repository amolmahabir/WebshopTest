/**
 * 
 */
app.controller('productCtrl', function($scope, $http) {
	$http.get('http://localhost:8080/Webshop/rest/products')
	.success(function(data, status, headers, config){
		$scope.Products = data;
	});
	$scope.postProductToCart = function (product) {
		console.info("called The method to post product ");
		var data = product;

		var config = {
				headers : {
					'Content-Type': 'application/json;'
				}
		}

		$http.post('http://localhost:8080/Webshop/rest/cart', data, config)
		.success(function (data, status, headers, config) {
			console.info("successfully processed request:"+data);

		})
		.error(function (data, status, header, config) {
			$scope.error=data;
			console.error("error:"+data);
		});
	};

});

app.controller('numberInputCtrl', ['$scope', function($scope) {
	$scope.numberInput = {
			value: 1        
	};
}]);


app.controller('cartCtrl', function($scope, $http) {
	$http.get('http://localhost:8080/Webshop/rest/cart')
	.success(function(data, status, headers, config){
		$scope.Products = data;

		$scope.costs = function (data) {
			var cost = data.price * data.amount;
			return cost;
		}

		$scope.totalPrice = function() {
			var total = 0;
			if($scope.Products.size() != 0){
				console.log($scope.Products.size())
				for(var i = 0; i < $scope.Products.length; i++){
					var product = $scope.Products[i];
					total += (product.price * product.amount);	
					console.log('tot hier goed');

					if (product.amount == 0){
						$scope.deleteProduct(product);
						$scope.Products.pop(product);

					}


				}
			}

			return total;

		}
	});


	$scope.countCartItems = function() {
		var count = 0;
		for(var j = 0; j < $scope.Products.length;j++){
			var cartItem = $scope.Products[j];
			count += cartItem.amount;
		}
		return count;
	}

	$scope.deleteProduct = function (product) {
		console.info("called The method to delete product ");
		var data = product;

		var config = {
				headers : {
					'Content-Type': 'application/json;'
				}
		}

		$http.delete('http://localhost:8080/Webshop/rest/cart/' + data.id)
		.success(function (data, status, headers) {
			console.info("successfully processed request:"+data);
			$scope.response = data;

			$http.get('http://localhost:8080/Webshop/rest/cart')
			.success(function(data, status, headers, config){
				$scope.Products = data;
			});
		})
		.error(function (data, status, header, config) {
			$scope.error=data;
			console.error("error:"+data);
		});
	};

});

app.config(['$routeProvider', '$locationProvider',
            function($routeProvider, $locationProvider) {
	$routeProvider
	/*.when('/rest/cart', {
               templateUrl: 'cart.html',

             })*/
	.when('/index2.html', {
		templateUrl: 'index2.html',

	});

	//$locationProvider.html5Mode(true);
}])
.controller('viewCtrl', ['$route', '$routeParams', '$location',
                         function viewCtrl($route, $routeParams, $location) {
	this.$route = $route;
	this.$location = $location;
	this.$routeParams = $routeParams;
}])
/*  .controller('cartViewCtrl', ['$routeParams', function cartViewCtrl($routeParams) {
         this.name = 'cartViewCtrl';
         this.params = $routeParams;
       }])
       .controller('ChapterCtrl', ['$routeParams', function ChapterCtrl($routeParams) {
         this.name = 'ChapterCtrl';
         this.params = $routeParams;
       }]); */