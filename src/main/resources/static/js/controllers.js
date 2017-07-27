app.controller('ToolbarCtrl', function ($scope, $rootScope, $timeout, $mdSidenav) {
    $rootScope.toggleLeftSideNav = buildToggler('left');

    function buildToggler(componentId) {
        return function () {
            $mdSidenav(componentId).toggle();
        };
    }
});

app.controller('ViewCtrl', function ($scope, $rootScope) {
    $scope.$on('$routeChangeStart', function (next, current) {
        $rootScope.loading = true;
    });

    $scope.$on('$viewContentLoaded', function () {
        $rootScope.loading = false;
    });
});

app.controller('LoadingController', function ($scope, $rootScope) {
});

app.controller('MenuLeftController', function ($scope, $rootScope, $http) {
    $scope.selectedMenuItemId = null;

    $scope.$on('$routeChangeStart', function (next, current) {
        $scope.checkHashForActiveMenu($scope.menuItems);
    });

    $scope.checkHashForActiveMenu = function (menuItems) {
        var winHash = window.location.hash;

        menuItems.forEach(function (item) {
            if (item.link === winHash) {
                $scope.setActiveMenu(item);
                return;
            }
        })
    };

    $scope.onMenuItemClick = function (menuItem) {
        if (menuItem !== null && $scope.selectedMenuItemId !== menuItem.id) {
            $rootScope.loading = true;
            $rootScope.toggleLeftSideNav();
            $scope.setActiveMenu(menuItem);
        }
    };

    $scope.setActiveMenu = function (menuItem) {
        if (menuItem !== null && menuItem.id !== null)
            $scope.selectedMenuItemId = menuItem.id;
    };

    $scope.isMenuActive = function (menuItem) {
        if (menuItem === null || menuItem.id === null || $scope.selectedMenuItemId === null)
            return false;
        return menuItem.id === $scope.selectedMenuItemId;
    };

    $http.get("/resources/data/menu.json")
        .success(function (data) {
            $scope.menuItems = data;

            $scope.checkHashForActiveMenu($scope.menuItems);
        });

    $http.get("/resources/data/currency_pairs.json", {dataType: 'jsonp'})
        .success(function (data) {
            $scope.currPairs = data;
            $rootScope.tickers = [];
            var found;

            $scope.currPairs.forEach(function (pair) {
                found = false;

                $rootScope.tickers.forEach(function (ticker) {
                    if (pair !== null && ticker !== null && pair.name === ticker.currencyPair) {
                        pair.ticker = ticker;
                        found = true;
                        return;
                    }
                });

                if (!found)
                    $http.get("/api/currency/ticker?currency_pair_id=" + pair.pair_id + '&stock_id=' + pair.stock_id)
                        .success(function (data) {
                            pair.ticker = data;
                            $rootScope.tickers.push(data);
                        });

            });
        });

    $http.get("/resources/data/stocks.json")
        .success(function (data) {
            $scope.stocks = data;
        });
});

app.controller('StockController', function ($scope) {

});

app.controller('TXListController', function ($scope, $rootScope, $mdEditDialog, $http, $route, $interval, api, api_tx, angularGridInstance, $timeout) {
    $scope.selected = [];
    $scope.txs = [];
    $scope.txsMeta = [];

    $scope.settings = {
        layout: 'cards',
        limit: 5,
        page: 0,
        limitAvailable: [
            {name: '5 items per page', value: 5},
            {name: '10 items per page', value: 10},
            {name: '20 items per page', value: 20},
            {name: '30 items per page', value: 30},
            {name: 'All items', value: -1}
        ],
        order: '-date',
        ticker: {
            updateInterval: 30000, // 30 sec
            intervals: [
                {name: '1 second', value: 1000},
                {name: '5 seconds', value: 5000},
                {name: '10 seconds', value: 10000},
                {name: '20 seconds', value: 20000},
                {name: '30 seconds', value: 30000}
            ]
        }
    };

    $scope.layout = {
        table: {
            name: 'table',
            selected: []
        },
        cards: {
            name: 'cards'
        }
    };

    $scope.onSelect = function (item) {
    };

    $scope.onDeselect = function (item) {
    };

    $scope.setTickerUpdateInterval = function (interval) {
        $interval.cancel($scope.tickerInterval);
        $scope.settings.ticker.updateInterval = interval;
        $scope.tickerInterval = $interval($scope.updateTickers, $scope.settings.ticker.updateInterval);
    };

    $scope.setLimit = function () {
        $scope.getTxs();
    };

    $scope.getTxs = function () {
        $scope.txs = [];

        api_tx.getPaged($scope.settings.page, $scope.settings.limit)
            .success(function (data) {
                $scope.txsMeta = data;
                $scope.txs = data.content;

                $timeout(angularGridInstance.cards.windowResizeCallback, 1000); // Bug fixed

                api_tx.addTickers($scope.txs);
            })
            .error(function (error) {
                // TODO handle error
                console.log(error);
            });
    };

    $scope.updateTickers = function () {
        api_tx.updateTickers($scope.txs);
    };

    $scope.editNote = function (event, transaction) {
        event.stopPropagation();

        var promise = $mdEditDialog.large({
            modelValue: transaction.notes,
            placeholder: 'Add a note',
            title: 'Edit note',
            save: function (input) {
                transaction.notes = input.$modelValue;
            },
            targetEvent: event,
            validators: {
                'md-maxlength': 500
            }
        });

        promise.then(function (ctrl) {
            var input = ctrl.getInput();

            input.$viewChangeListeners.push(function () {
                input.$setValidity('test', input.$modelValue !== 'test');
            });
        });
    };

    $scope.getTxs();
    $scope.tickerInterval = $interval($scope.updateTickers, $scope.settings.ticker.updateInterval);
});