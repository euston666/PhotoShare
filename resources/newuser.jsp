<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.UserDao" %>
<jsp:useBean id="newUserBean"
             class="photoshare.NewUserBean" />
<jsp:setProperty name="newUserBean" property="*" />

<html>
    <head><title>User Registration</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>

        <h1>User Registration</h1>

        <%
        boolean isBlank = true;
        boolean isComplete = true;
        boolean successful = false;
        int result = -1;
        String err = null;
        %>
        
        <%
        if (newUserBean.getEmail().equals("")) { isComplete = false; } else { isBlank = false; }
        if (newUserBean.getLast().equals("")) { isComplete = false; } else { isBlank = false; }
        if (newUserBean.getFirst().equals("")) { isComplete = false; } else { isBlank = false; }
        if (newUserBean.getGender().equals("")) { isComplete = false; } else { isBlank = false; }
        if (newUserBean.getBirth().equals("")) { isComplete = false; } else { isBlank = false; }
        if (newUserBean.getEdu().equals("")) { isComplete = false; } else { isBlank = false; }
        if (newUserBean.getHometowncity().equals("")) { isComplete = false; } else { isBlank = false; }
        if (newUserBean.getHometownstate().equals("")) { isComplete = false; } else { isBlank = false; }
        if (newUserBean.getHometowncountry().equals("")) { isComplete = false; } else { isBlank = false; }
        if (newUserBean.getCurrentcity().equals("")) { isComplete = false; } else { isBlank = false; }
        if (newUserBean.getCurrentstate().equals("")) { isComplete = false; } else { isBlank = false; }
        if (newUserBean.getCurrentcountry().equals("")) { isComplete = false; } else { isBlank = false; }

        if (!isComplete && !isBlank) {
            err = "Please input all the required information.";
        }
        if (!isBlank && isComplete && (newUserBean.getPassword1().length() < 4 || newUserBean.getPassword2().length() < 4)) {
            err = "Please choose a password longer than 4 characters.";
        }
        if (!isBlank && isComplete && !newUserBean.getPassword1().equals(newUserBean.getPassword2())) {
            err = "Password inputs are not the same.";
        }
        if (!isBlank && isComplete && !newUserBean.getBirth().matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
            err = "Please input date of birth in format \"yyyy-mm-dd\".";
        }
        if (!isBlank && isComplete && !newUserBean.getEmail().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            err = "Please input a valid email.";
        }

        if (isComplete && err == null) {
            UserDao userDao = new UserDao();
            result = userDao.create(newUserBean.getEmail(), newUserBean.getPassword1(),
                                    newUserBean.getFirst(), newUserBean.getLast(),
                                    newUserBean.getGender(), newUserBean.getBirth(),
                                    newUserBean.getEdu(), newUserBean.getHometowncity(),
                                    newUserBean.getHometownstate(), newUserBean.getHometowncountry(),
                                    newUserBean.getCurrentcity(), newUserBean.getCurrentstate(),
                                    newUserBean.getCurrentcountry());
        }
        %>
        
        <%
        if (err != null) { %>
            <font color=red><b><%= err %></b></font>
            <%
        }

        if (result == 1) { %>
            <br><br>
            <b>User <%= newUserBean.getEmail() %> is created.</b>
            <br><br><br>
            <%
        }

        if (result == 0) { %>
            <br><br>
            <b>User <%= newUserBean.getEmail() %> already exists.</b>
            <br><br><br>
            <b>Continue to <a href="newuser.jsp">Create another account</a>.</b>
            <%
        }
        %>

        <%
        if (!isComplete || isBlank || err != null) { %>

            <form method="POST" action="newuser.jsp">
                <table>
                <tr><th width="40%" align="right">First Name: </th><td align="left"><input type="text" name="first"></td></tr>
                <tr><th width="40%" align="right">Last Name: </th><td align="left"><input type="text" name="last"></td></tr>
                <tr><th width="40%" align="right">Date of Birth (yyyy-mm-dd): </th><td align="left"><input type="text" name="birth"></td></tr>
                <tr><th width="40%" align="right">Email: </th><td align="left"><input type="text" name="email"></td></tr>
                <tr><th width="40%" align="right">Password (length > 4): </th><td align="left"><input type="password" name="password1"></td></tr>
                <tr><th width="40%" align="right">Re-enter Password: </th><td align="left"><input type="password" name="password2"></td></tr>
                <tr><th width="40%" align="right">Male: </th><td align="left"><input type="radio" name="gender" value="male"></td></tr>
                <tr><th width="40%" align="right">Female: </th><td align="left"><input type="radio" name="gender" value="female"></td></tr>
                <tr><th width="40%" align="right">Education Level: </th><td align="left"><input type="text" name="edu"></td></tr>
                <tr><th width="40%" align="right">Hometown City: </th><td align="left"><input type="text" name="hometowncity"></td></tr>
                <tr><th width="40%" align="right">Hometown State: </th><td align="left"><input type="text" name="hometownstate"></td></tr>
                <tr><th width="40%" align="right">Hometown Country: </th><td align="left"><input type="text" name="hometowncountry"></td></tr>
                <tr><th width="40%" align="right">Current City: </th><td align="left"><input type="text" name="currentcity"></td></tr>
                <tr><th width="40%" align="right">Current State: </th><td align="left"><input type="text" name="currentstate"></td></tr>
                <tr><th width="40%" align="right">Current Country: </th><td align="left"><input type="text" name="currentcountry"></td></tr>
                <tr><th width="40%" align="right"></th><td align="left"><input type="submit" value="Create"/></td></tr>
                </table>
            </form>
            <%
        }
        %>

        <br>
        <b>Return to <a href="login.jsp">Login Page</a>.</b>
    </body>
</html>
