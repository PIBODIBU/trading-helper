<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<md-toolbar md-whiteframe="4"
            style="padding-left:8px; z-index: 3">
    <div class="md-toolbar-tools">
        <div flex layout="row" layout-align="start center">
            <span class="md-headline" style="color: #2196F3">Futoria</span>
            <span>&nbsp;</span>
            <span class="md-headline"
                  style="color: #757575; font-family: RobotoLight; font-weight: bolder">Tests</span>

            <md-autocomplete
                    style="margin-left:16px;"
                    flex="40"
                    hide-xs
                    md-selected-item="ctrl.selectedItem"
                    md-search-text-change="ctrl.searchTextChange(ctrl.searchText)"
                    md-search-text="ctrl.searchText"
                    md-selected-item-change="ctrl.selectedItemChange(item)"
                    md-items="item in ctrl.querySearch(ctrl.searchText)"
                    md-item-text="item.display"
                    md-min-length="0"
                    placeholder="Введіть пошуковий запит...">
                <md-item-template>
                    <span md-highlight-text="ctrl.searchText" md-highlight-flags="^i">{{item.display}}</span>
                </md-item-template>
                <md-not-found>
                    No states matching "{{ctrl.searchText}}" were found.
                    <a ng-click="ctrl.newState(ctrl.searchText)">Create a new one!</a>
                </md-not-found>
            </md-autocomplete>
        </div>

        <%--<h2 flex md-truncate> Welcome, ${pageContext.request.userPrincipal.name}</h2>--%>
        <md-menu md-position-mode="target-right target">
            <md-button class="md-icon-button"
                       aria-label="Modules"
                       ng-click="ctrl.openMenu($mdMenu, $event)">
                <md-icon md-svg-icon="apps" style="fill: #757575;!important;"></md-icon>
            </md-button>

            <md-menu-content width="4"
                             layout="column"
                             layout-align="center stretch">
                <md-menu-item>
                    <div layout="column"
                         ng-init="h=1;"
                         ng-mouseenter="h=4;"
                         ng-mouseleave="h=1;"
                         md-whiteframe="{{h}}"
                         layout-align="center center"
                         ng-repeat="item in [1,2,3]">
                        <img src="https://maxcdn.icons8.com/Share/icon/Logos//google_logo1600.png"
                             style="max-height: 56px; max-width: 56px"/>
                        <span class="md-caption">Module {{item}}</span>
                    </div>
                </md-menu-item>

                <md-menu-item>
                </md-menu-item>

                <md-menu-item>
                    <div layout="column"
                         ng-init="h=1;"
                         ng-mouseenter="h=4;"
                         ng-mouseleave="h=1;"
                         md-whiteframe="{{h}}"
                         layout-align="center center"
                         ng-repeat="item in [4,5,6]">
                        <img src="https://maxcdn.icons8.com/Share/icon/Logos//google_logo1600.png"
                             style="max-height: 56px; max-width: 56px"/>
                        <span class="md-caption">Module {{item}}</span>
                    </div>
                </md-menu-item>

                <md-menu-item>
                </md-menu-item>

                <md-menu-item>
                    <md-button ng-click="null"
                               class="md-accent">
                        Futoria - домашня
                    </md-button>
                </md-menu-item>
            </md-menu-content>
        </md-menu>

        <md-button class="md-icon-button"
                   aria-label="Settings">
            <md-icon md-svg-icon="bell"
                     style="fill: #757575;!important;"></md-icon>
        </md-button>

        <md-button class="md-icon-button"
                   aria-label="Settings">
            <md-icon md-svg-icon="account"
                     style="fill: #757575;!important;"></md-icon>
        </md-button>
    </div>
</md-toolbar>