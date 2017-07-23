'use strict';

var app = angular.module('BaseApp',
    ['ngMaterial', 'ngMessages', 'material.svgAssetsCache', 'ngAnimate']);

app.config(function ($mdIconProvider, $mdThemingProvider) {
    $mdIconProvider.defaultIconSet('/resources/icons/mdi.svg');

    $mdThemingProvider.definePalette('white', {
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
        'contrastDefaultColor': 'dark'
    });

    $mdThemingProvider.theme('default')
        .primaryPalette('white')
        .accentPalette('blue')
        .backgroundPalette('grey',
            {
                'default': '100'
            });

    $mdThemingProvider.enableBrowserColor({
        theme: 'default', // Default is 'default'
        palette: 'accent', // Default is 'primary', any basic material palette and extended palettes are available
        hue: '800' // Default is '800'
    });
});

app.controller('StatisticsController', ['$scope', function ($scope) {
}]);

app.controller('SearchBarController', function ($scope) {
});