var app = angular.module('BlankApp', ['ngMaterial', 'ngRoute', 'md.data.table']);

app.config(function ($mdThemingProvider) {
    $mdThemingProvider
        .theme('default')
        .primaryPalette('blue-grey')
        .accentPalette('orange');
});

app.config(function ($mdIconProvider) {
    $mdIconProvider
        .defaultIconSet('/resources/icons/mdi.svg')
});

app.config(function ($routeProvider) {
    $routeProvider
        .when("/btc", {
            templateUrl: "/resources/template/stock_btc.tmpl.html"
        })
        .when("/tx_list", {
            templateUrl: "/resources/template/tx_list.tmpl.html"
        })
        .when("/tx_add", {
            templateUrl: "/resources/template/tx_add.tmpl.html"
        })
        .otherwise({
            templateUrl: '/resources/template/stock_btc.tmpl.html'
        })
});

app.directive('capitalize', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, modelCtrl) {
            var capitalize = function (inputValue) {
                if (inputValue === undefined) inputValue = '';
                var capitalized = inputValue.toUpperCase();
                if (capitalized !== inputValue) {
                    modelCtrl.$setViewValue(capitalized);
                    modelCtrl.$render();
                }
                return capitalized;
            };
            modelCtrl.$parsers.push(capitalize);
            capitalize(scope[attrs.ngModel]); // capitalize initial value
        }
    };
});