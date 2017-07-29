var app = angular.module('TradingHelperApp', ['ngMaterial', 'ngRoute', 'md.data.table', 'angularGrid']);

/*Configs*/
// Themes
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

// Icons
app.config(function ($mdIconProvider) {
    $mdIconProvider
        .defaultIconSet('/resources/icons/mdi.svg')
});

// Routes
app.config(function ($routeProvider) {
    $routeProvider
        .when("/tx_list", {
            templateUrl: "/resources/template/tx_list.tmpl.html"
        })
        .when("/t", {
            templateUrl: "/resources/template/tx_list.tmpl.html"
        })
        .when("/rate", {
            templateUrl: "/resources/template/stock_list.tmpl.html"
        })
    /*.otherwise({
        templateUrl: "/resources/template/tx_list.tmpl.html"
    })*/
});

/*Directives*/
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

/*Filters*/
app.filter('msToDate', function () {
    return function (ms) {
        return new Date(ms).customFormat("#DD#/#MM#/#YYYY# #hhhh#:#mm#:#ss#");
    }
});

app.filter('currencyBase', function () {
    return function (currency) {
        return currency.split('/')[0];
    }
});

app.filter('currencyCounter', function () {
    return function (currency) {
        return currency.split('/')[1];
    }
});

/*Services*/
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

app.service('api', function () {
    this.URL = '/api/v1/';

    this.methods = {GET: 'GET', POST: 'POST'};

    this.prepareParams = function (url, params, method) {
        switch (method) {
            case this.methods.GET:
                url += '?';

                params.forEach(function (param) {
                    if (param !== null && param.name !== null && param.value !== null)
                        url = url.concat(param.name).concat('=').concat(param.value).concat('&');
                });
        }

        return url;
    }
});

app.service('api_tx', function ($http, api, api_ticker) {
    this.getPaged = function (page, size) {
        return $http.get(api.prepareParams(api.URL + 'tx/list',
            [
                {name: 'page', value: page},
                {name: 'size', value: size}
            ],
            api.methods.GET));
    };

    this.addTickers = function (txs) {
        var found;
        var tickers = [];

        txs.forEach(function (tx) {
                found = false;

                tickers.forEach(function (ticker) {
                    if (tx.currencyPair !== null && ticker !== null && tx.currencyPair.name === ticker.currencyPair) {
                        tx.ticker = ticker;
                        found = true;
                        return;
                    }
                });

                if (!found) {
                    api_ticker.getByPairIdAndStockId(tx.currencyPair.id, tx.stock.id)
                        .success(function (data) {
                            tx.ticker = data;
                            tickers.push(data);
                        })
                }
            }
        )
    };

    this.updateTickers = function (txs) {
        this.addTickers(txs);
    }
});

app.service('api_ticker', function ($http, api) {
    this.getByPairIdAndStockId = function (pairId, stockId) {
        return $http.get(api.prepareParams(api.URL + 'currency/ticker',
            [
                {name: 'currency_pair_id', value: pairId},
                {name: 'stock_id', value: stockId}
            ],
            api.methods.GET));
    };
});

app.service('api_stock', function ($http, api) {
    this.get = function (id) {
        return $http.get(api.prepareParams(api.URL + 'stock/get',
            [
                {name: 'stock_id', value: id}
            ],
            api.methods.GET));
    };

    this.getPaged = function (page, size) {
        return $http.get(api.prepareParams(api.URL + 'stock/list',
            [
                {name: 'page', value: page},
                {name: 'size', value: size}
            ],
            api.methods.GET));
    };
});

app.service('api_rate', function ($http, api) {
    this.getPagedByStock = function (page, size, stock_id) {
        return $http.get(api.prepareParams(api.URL + 'rate/list/stock',
            [
                {name: 'page', value: page},
                {name: 'size', value: size},
                {name: 'stock_id', value: stock_id}
            ],
            api.methods.GET));
    };

    this.getPaged = function (page, size) {
        return $http.get(api.prepareParams(api.URL + 'rate/list',
            [
                {name: 'page', value: page},
                {name: 'size', value: size}
            ],
            api.methods.GET));
    };
});