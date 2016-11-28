/**
 * 
 */

//Service for translations
app.config(function($translateProvider) {
	//Dutch
	$translateProvider.translations('nl', {
		//Main page text
		HEADER: 'Welkom bij Pet Supplies Web Store, de plek op het net om op een snelle en simpele manier de benodigde spullen voor onze dieren vrienden te kopen',
		FOOTER1: 'Voor aankoop, stuur een mail naar de benedenstaande email adres met de producten en hun aantal.,',
		FOOTER2: 'Gelieve uw contact informatie ook te vermelden.',
		TITLE: 'Pet Supplies Online Webshop!',

		//Product texts
		NAME01: 'Ballen',
		DESCRIPTION01: 'Een ronde rubbere luchtgevulde speelobject',
		CATEGORY01: 'Speelgoed',

		NAME02: 'Kauwbot',
		DESCRIPTION02: 'Een kauwbaar speeltje in de vorm van een bot',
		CATEGORY02: 'Gebit',

		NAME03: 'Knuffel',
		DESCRIPTION03: 'Een zacht beertje om te knuffelen',
		CATEGORY03:'Speelgoed',

		NAME04: 'Hondenvoer',
		DESCRIPTION04: 'Een 5 kilo zak gezond hondenvoer',
		CATEGORY04:'Voer',

		NAME05: 'Luizen shampoo',
		DESCRIPTION05: 'Effectieve shampoo om luizen te bestreiden',
		CATEGORY05:'Verzorging',

		NAME06: 'Kattenpaal',
		DESCRIPTION06: 'Een stevige constructie waar katten in kunnen spelen',
		CATEGORY06: 'Speelgoed',

		nl:'Nederlands',
		en: 'English'

	})

//	English
	.translations('en', {
		TITLE: 'Pet Supplies Online Webshop!',
		//Main page text
		HEADER: 'Welcome to our Pet Supplies Web Store, the place to be on the internet to quickly browse and buy products for our animal friends.',
		FOOTER1: 'For purchase, please mention your desired product and quantity to the email below.',
		FOOTER2: 'Please also mention your contact details.',

		//Product texts
		NAME01: 'Balls',
		DESCRIPTION01: 'A round rubber bouncy ball',
		CATEGORY01: 'Toys',

		NAME02: 'Chewing bone',
		DESCRIPTION02: 'A chewable object in the shape of a bone',
		CATEGORY02: 'Dental',

		NAME03: 'Stuffed toy',
		DESCRIPTION03: 'A soft stoffed toy as a bear',
		CATEGORY03:'Toys',

		NAME04: 'Dog food',
		DESCRIPTION04: 'A 5 kilo bag of dog food',
		CATEGORY04:'Feed',

		NAME05: 'Anti-flea shampoo',
		DESCRIPTION05: 'A shampoo that effectively removes fleas',
		CATEGORY05:'Care',

		NAME06: 'Cat pole',
		DESCRIPTION06: 'A sturdy construction in which cats can play',
		CATEGORY06: 'Toys',

		nl:'Nederlands',
		en: 'English'

	});


	$translateProvider.preferredLanguage('en');
})


//Logic for currency conversions


.controller('productCtrl', function($scope, $http) {
	//Logic for listing unique categories
	$scope.Products = [];
	$scope.getCategoryArray = function(){
		var categoryArray = [];
		for(var i = 0; i < $scope.Products.length; i++){
			var product = $scope.Products[i];
			categoryArray[i] = product.category;
		}
		var uniqueCat = categoryArray.reduce(function(a,b){
			if (a.indexOf(b)<0)a.push(b);
			return a;
		},[]);
		
		return uniqueCat;
	}

	//Logic for using categories as a filter in lists
	$scope.categoryfilter = "";
	$scope.setCategoryFilter = function(category){
		$scope.categoryfilter  = category;
		//console.log(''+$scope.getCategoryFilter());
		//console.log(''+$scope.categoryfilter);
		console.log($scope.Products)
		$scope.filteredProducts = $scope.Products.filter(function(fp){
			if ($scope.getCategoryFilter()===''){
				return $scope.Products;
			} else return fp.category === $scope.getCategoryFilter();
		})
	}

	$scope.getCategoryFilter = function(){
		return $scope.categoryfilter;
	}

	// Logic for pushing a product to the Cart REST service
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
		$http.get('http://localhost:8080/Webshop/rest/cart')
		.success(function(data, status, headers, config){
			$scope.Products = data;
		})
		.error(function (data, status, header, config) {
			$scope.error=data;
			console.error("error:"+data);
		});
		$scope.$apply();
	};
})


//Controller for number input field in cart
.controller('numberInputCtrl', ['$scope', function($scope) {
	$scope.numberInput = {
			value: 1        
	};
}])


//Controller for cart operations
.controller('cartCtrl', function($scope, $http) {
	$http.get('http://localhost:8080/Webshop/rest/cart')
	.success(function(data, status, headers, config){
		$scope.Products = data;

		$scope.costs = function (data) {
			var cost = data.price * data.amount;
			return cost;
		}

		//Logic for when the amount gets changed
		$scope.totalPrice = function() {
			var total = 0;
			for(var i = 0; i < $scope.Products.length; i++){
				var product = $scope.Products[i];
				total += (product.price * product.amount);	
				console.log('tot hier goed');

				if (product.amount == 0){
					$scope.deleteProduct(product);
					$scope.Products.pop(product);

				}
			}
			return total;
		}
		$scope.countCartItems = function() {
			var count = 0;
			for(var j = 0; j < $scope.Products.length;j++){
				var cartItem = $scope.Products[j];
				count += cartItem.amount;
			}
			return count;
		}
	});
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
})

//Route provider for working with different views
.config(['$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/cart', {
		templateUrl: 'cart.html',


	})
	.otherwise('/main', {
		templateUrl: 'http://localhost:8080/Webshop/main.html',

	})
	.when('/main', {
		templateUrl: 'http://localhost:8080/Webshop/main.html',
	})
	.when('/signup', {
			templateUrl: 'http://localhost:8080/Webshop/signup.html',

	});
}])
.controller('viewCtrl', ['$route', '$routeParams', '$location',
                         function viewCtrl($route, $routeParams, $location) {
	this.$route = $route;
	this.$location = $location;
	this.$routeParams = $routeParams;
}])
/*.controller('globalCtrl', ['currency', '$scope', '$translate',function(currency, $scope, $translate){
	//Logic for currency selection
	$scope.ctrl = this;

	$scope.ctrl.currency = '€';
	$scope.ctrl.currencies = ['€', '$', '£'];
	//$scope.ctrl.updateCurrency = function() {
	//$scope.setCurrency($scope.ctrl.currency);
	//};

	$scope.ctrl.setCurrency = function(cur){
		$scope.ctrl.currency = cur;
	}

	$scope.ctrl.getCurrency = function(){
		return $scope.ctrl.currency;
	}

	//Logic for language selection
	$scope.ctrl.language = 'nl';  
	$scope.ctrl.languages = ['nl', 'en'];
	$scope.ctrl.updateLanguage = function() {
		$translate.use($scope.ctrl.language);
	}

}])
.filter('currency', function($rootScope) {
	var defaultCurrency = $scope.ctrl.getCurrency();

	return function(input, currencySymbol) {
		var out = "";
		currencySymbol = currencySymbol || defaultCurrency;

		switch(currencySymbol) {
		case '£':
			out = 0.86 * input; // google
			break;

		case '$':
			out = 1.07 * input;
			break;

		default: 
			out = input;
		}

		return currencySymbol + ' ' + out.toFixed(2);
	}
});
*/
