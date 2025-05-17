<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Προβολή Αντικειμένων</title>
</head>
<body>
    <h1>Προβολή Όλων των Αντικειμένων</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Περιγραφή</th>
            <th>Ευρών</th>
            <th>Τοποθεσία</th>
            <th>Ενέργειες</th>
        </tr>
        <%
        
        String dbPath = getServletContext().getRealPath("/WEB-INF/laf.db");	
        String dbURL = "jdbc:sqlite:" + dbPath; 
            try {
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection(dbURL);
                
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id, description, finder, location FROM LostAndFound");

                boolean hasData = false; // Έλεγχος αν υπάρχουν εγγραφές
                while (rs.next()) {
                    hasData = true;
        %>
        <tr>
            <td><%= rs.getInt("id") %></td>
            <td><%= rs.getString("description") %></td>
            <td><%= rs.getString("finder") %></td>
            <td><%= rs.getString("location") %></td>
            <td>
                <form action="controller" method="POST">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%= rs.getInt("id") %>">
                    <button type="submit">Διαγραφή</button>
                </form>
            </td>
        </tr>
        <%
                }
                if (!hasData) {
                    out.println("<tr><td colspan='5'>Δεν βρέθηκαν εγγραφές στη βάση δεδομένων.</td></tr>");
                }
                conn.close();
            } catch (Exception e) {
                out.println("<tr><td colspan='5'>Σφάλμα: " + e.getMessage() + "</td></tr>");
            }
        %>
    </table>
    <a href="index.jsp">Επιστροφή στην Αρχική Σελίδα</a>
</body>
</html>
