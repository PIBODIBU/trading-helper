<%@ page import="com.helper.trading.application.configuration.security.UserDetails" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.helper.trading.model.user.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%
    User user = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
%>

<script type="text/javascript">
    var menuItems = [];

    menuItems.push(
        {
            'header': 'Навігація',
            'opened': true,
            'items': [
                {'icon': 'home', 'title': 'Головна'},
                {'icon': 'bell', 'title': 'Сповіщення'}
            ]
        }
    );
</script>

<sec:authorize access="hasRole('ROLE_SYS_ADMIN')">
    <script type="text/javascript">
        menuItems.push(
            {
                'header': 'Адміністратор системи',
                'items': [
                    {'icon': 'settings', 'title': 'Налаштування системи'},
                    {'icon': 'settings', 'title': 'Налаштування модулю'}]
            }
        );
    </script>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_UNI_ADMIN')">
    <script type="text/javascript">
        menuItems.push({
            'header': 'Адміністратор університету',
            'items': [
                {'icon': 'domain', 'title': 'Мій університет'},
                {'icon': 'account-multiple', 'title': 'Мої студенти'},
                {'icon': 'archive', 'title': 'Archive'},
                {'icon': 'account-multiple', 'title': 'Students'}
            ]
        });
    </script>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_UNI_STUDENT')">
    <script type="text/javascript">
        menuItems.push(
            {
                'header': 'Меню студента',
                'items': [
                    {'icon': 'file-document-box', 'title': 'Пройдені тести'},
                    {'icon': 'account-multiple', 'title': 'Моя група'},
                    {'icon': 'domain', 'title': 'Мій університет'},
                    {'icon': 'account', 'title': 'Мій куратор'},
                    {'icon': 'account', 'title': 'Мій староста'}
                ]
            }
        );
    </script>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_UNI_PROFESSOR')">
    <script type="text/javascript">
        menuItems.push(
            {
                'header': 'Меню викладача',
                'items': [
                    {'icon': 'file-document-box', 'title': 'Мої тести', 'link': '/me/tests/'}
                ]
            }
        );
    </script>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
    <script type="text/javascript">
        app.controller('SideNavCtrl', function ($scope) {
            $scope.menuItems = menuItems;

            this.dispatchItemClick = function (menuItem) {
                window.location.href = menuItem.link;
            }
        });
    </script>

    <style>
        .side-block-user_card {
            margin: 16px 8px 8px 8px;
            min-width: 250px;
            max-width: 250px;
        }

        .side-block {
            min-width: 250px;
            max-width: 250px;
            margin-right: 16px;
        }

        md-list.md-dense md-list-item.md-2-line, md-list.md-dense md-list-item.md-2-line > .md-no-style {
            max-height: 2.5em;
            min-height: initial;
        }
    </style>

    <md-sidenav
            class="side-block md-sidenav-left"
            md-component-id="left"
            style="background: transparent"
            style="z-index: 1"
            md-is-locked-open="$mdMedia('gt-sm')">
        <div layout="row"
             md-whiteframe="3"
             style="background: white; padding:16px; margin-right: 8px; margin-top: 8px; min-height: 82px; z-index: 2">
            <div class="md-user-avatar image-cropper-middle md-user-avatar">
                <img ng-src="https://pp.userapi.com/c626518/v626518261/4ff9d/bH7BfQjE924.jpg"
                     class="avatar">
            </div>

            <div layout="column"
                 style="padding: 8px;">
                    <%--<span class="md-body-1">{{user.firstName}} {{user.lastName}}</span>--%>
                <span class="md-body-1"><%=user.getFirstName()%> <%=user.getLastName()%></span>
                <span class="md-caption" style="color: #616161"><%=user.getEmail()%></span>
            </div>
        </div>

        <md-content style="background: transparent;"
                    ng-controller="SideNavCtrl as ctrl">
            <div md-whiteframe="3"
                 style="background: white; margin: 16px 8px 16px 0"
                 ng-repeat="menuItem in menuItems">

                <md-list-item ng-click="menuItem.opened = !menuItem.opened"
                              style="font-size: 14px">
                    {{menuItem.header}}
                    <span flex></span>
                    <md-icon md-svg-icon="chevron-up" ng-if="menuItem.opened"></md-icon>
                    <md-icon md-svg-icon="chevron-down" ng-if="!menuItem.opened"></md-icon>
                </md-list-item>

                <md-divider ng-show="menuItem.opened"></md-divider>

                <md-list flex
                         ng-show="menuItem.opened"
                         class="md-dense">
                    <md-list-item ng-click="ctrl.dispatchItemClick(listItem)"
                                  ng-repeat="listItem in menuItem.items"
                                  class="md-2-line">
                        <md-icon md-svg-icon="{{listItem.icon}}"
                                 style="margin-right: 24px; margin-top: 8px; margin-bottom: 6px"></md-icon>
                        <div class="md-list-item-text">
                            <h3>{{listItem.title}}</h3>
                        </div>
                    </md-list-item>
                </md-list>
            </div>

                <%--<md-list flex
                         class="md-dense"
                         ng-controller="SideNavCtrl">
                    <div ng-repeat="item in menuItems">
                        <md-list-item ng-click="null"
                                      class="md-2-line"
                                      ng-if="item.title != null">
                            <md-icon md-svg-icon="{{item.icon}}"
                                     style="margin-right: 24px; margin-top: 8px; margin-bottom: 6px"></md-icon>
                            <div class="md-list-item-text">
                                <h3>{{item.title}}</h3>
                            </div>
                        </md-list-item>

                        <md-divider ng-if="!item.title && !item.header"
                                    style="margin-top: 8px; margin-bottom:8px;"></md-divider>

                        <md-subheader ng-if="item.header">{{item.header}}
                        </md-subheader>
                    </div>
                </md-list>--%>
        </md-content>
    </md-sidenav>
</sec:authorize>