package laf2476;

// Εισαγωγή βιβλιοθηκών για servlet
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

// Εισαγωγή βασικών βιβλιοθηκών Java
import java.io.*;
import java.sql.*;

// Δήλωση της servlet με όνομα "controller"
@WebServlet("/controller")
public class Controller extends HttpServlet {
 private String dbURL; // Μεταβλητή για την αποθήκευση του URL της βάσης δεδομένων

 @Override
 public void init() throws ServletException {
     // Αρχικοποίηση του URL της βάσης δεδομένων (SQLite) με το σχετικό μονοπάτι
     String dbPath = getServletContext().getRealPath("/WEB-INF/laf.db");	
     dbURL = "jdbc:sqlite:" + dbPath; 
	}

 @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
     // Ανάκτηση της παράμετρου "action" από το αίτημα
     String action = request.getParameter("action");

     // Διαχείριση σύνδεσης με τη βάση δεδομένων χρησιμοποιώντας try-with-resources
     try (Connection conn = DriverManager.getConnection(dbURL)) {
         if ("delete".equals(action)) { // Έλεγχος αν η ενέργεια είναι "delete"
             // Ανάκτηση του ID που θα διαγραφεί από το αίτημα
             int id = Integer.parseInt(request.getParameter("id"));
             // Δημιουργία prepared statement για διαγραφή
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM LostAndFound WHERE id = ?");
             stmt.setInt(1, id); // Ορισμός της τιμής ID
             stmt.executeUpdate(); // Εκτέλεση της εντολής διαγραφής
             // Ανακατεύθυνση στη σελίδα "view_all.jsp"
             response.sendRedirect("view_all.jsp");
         } else { // Επεξεργασία εισαγωγής δεδομένων
             String description = request.getParameter("description"); // Περιγραφή αντικειμένου
             String finder = request.getParameter("finder"); // Πληροφορίες για τον ευρέτη
             String location = request.getParameter("location"); // Τοποθεσία εύρεσης

             // Έλεγχος για κενά πεδία
             if (description == null || description.trim().isEmpty() ||
                 finder == null || finder.trim().isEmpty() ||
                 location == null || location.trim().isEmpty()) {
                 // Αν βρεθούν κενά πεδία, ορισμός μηνύματος σφάλματος
                 request.setAttribute("errorMsg", "Σφάλμα εισαγωγής.");
                 // Προώθηση στη σελίδα "insert.jsp" με μήνυμα σφάλματος
                 request.getRequestDispatcher("insert.jsp").forward(request, response);
                 return; // Τερματισμός για αποφυγή περαιτέρω επεξεργασίας
             }

             // Δημιουργία prepared statement για εισαγωγή δεδομένων
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO LostAndFound (description, finder, location) VALUES (?, ?, ?)");
             stmt.setString(1, description); // Ορισμός της περιγραφής
             stmt.setString(2, finder); // Ορισμός του ευρέτη
             stmt.setString(3, location); // Ορισμός της τοποθεσίας
             stmt.executeUpdate(); // Εκτέλεση της εντολής εισαγωγής

             // Ορισμός μηνύματος επιτυχίας και προώθηση στη σελίδα "insert.jsp"
             request.setAttribute("message", "Η εγγραφή προστέθηκε με επιτυχία!");
             request.getRequestDispatcher("insert.jsp").forward(request, response);
         }
     } catch (Exception e) { // Διαχείριση εξαιρέσεων
         if ("delete".equals(action)) { // Σφάλμα στη διαγραφή
             request.setAttribute("error", e.getMessage());
             request.getRequestDispatcher("view_all.jsp").forward(request, response);
         } else { // Σφάλμα στην εισαγωγή
             request.setAttribute("errorMsg", e.getMessage());
             request.getRequestDispatcher("insert.jsp").forward(request, response);
         }
     }

 }
}
