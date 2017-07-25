var app = angular.module('BlankApp', ['ngMaterial', 'ngRoute', 'md.data.table', 'angularGrid']);

app.config(function ($mdThemingProvider) {
    $mdThemingProvider.definePalette('whitePalette', {
        '50': 'ffffff',
        '100': 'ffffff',
        '200': 'ffffff',
        '300': 'ffffff',
        '400': 'ffffff',
        '500': 'ffffff',
        '600': 'ffffff',
        '700': 'ffffff',
        '800': 'ffffff',
        '900': 'ffffff',
        'A100': 'ffffff',
        'A200': 'ffffff',
        'A400': 'ffffff',
        'A700': 'ffffff',
        'contrastDefaultColor': 'dark',    // whether, by default, text (contrast)
        // on this palette should be dark or light

        'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
            '200', '300', '400', 'A100'],
        'contrastLightColors': undefined    // could also specify this if default was 'dark'
    });
    $mdThemingProvider.theme('white')
        .primaryPalette('whitePalette')
        .backgroundPalette('whitePalette');

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

app.service('cookies', function () {
    this.set = function (name, value, days) {
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            var expires = "; expires=" + date.toGMTString();
        }
        else {
            var expires = "";
        }
        document.cookie = name + "=" + value + expires + "; path=/";
    };

    this.get = function (name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) === ' ') {
                c = c.substring(1, c.length);
            }
            if (c.indexOf(nameEQ) === 0) {
                return c.substring(nameEQ.length, c.length);
            }
        }
        return null;
    };

    this.delete = function (name) {
        set(name, "", -1);
    }
});