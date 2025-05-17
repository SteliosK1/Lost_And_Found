<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insert</title>
</head>
<body>
<h1 style="text-align:center;">Εισαγωγή Αντικειμένων</h1>
<form action="controller" method="POST">

		 <b>Παρακαλώ δώστε τα ακόλουθα στοιχεία: </b> <br>
        <label for="description">Περιγραφή:</label>
        <input type="text" id="description" name="description" value="${description != null ? description : ''}"><br>
        <label for="finder">Ευρών:</label>
        <input type="text" id="finder" name="finder" value="${finder != null ? finder : ''}"><br>
        <label for="location">Τοποθεσία:</label>
        <input type="text" id="location" name="location" value="${location != null ? location : ''}"><br>
        <button type="submit">Εισαγωγή</button>
    </form>
    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
            out.println("<p style='color: green;'>" + message + "</p>");
        }
    %>
    <%
        String errorMsg = (String) request.getAttribute("errorMsg");
        if (errorMsg != null) {
            out.println("<p style='color: red;'>" + errorMsg + "</p>");
        }
    %>
    <br>
    <a href="index.jsp">Επιστροφή στην αρχική σελίδα</a>
    <footer style = "position:center; bottom:0; text-align:center;">
        <p><strong>Δημιουργήθηκε από Stelios Koutsioumaris 2476</strong></p>
    </footer>
</body>
</html>