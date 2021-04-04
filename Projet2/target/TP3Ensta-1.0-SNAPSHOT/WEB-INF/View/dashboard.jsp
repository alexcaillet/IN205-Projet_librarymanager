<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library Management</title>
  <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="assets/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<body>
  <jsp:include page='menu.jsp'></jsp:include>
  <main>
    <section class="content">
      <div class="page-announce valign-wrapper">
        <a href="#" data-activates="slide-out" class="button-collapse valign hide-on-large-only"><i class="material-icons">menu</i></a>
        <h1 class="page-announce-text valign">Tableau de bord</h1>
      </div>
      <div class="row">
        <div class="col l4 s6">
          <div class="small-box bg-aqua">
            <div class="inner">
              <h3><% 
                Object n_membres = request.getAttribute("membres_count");
                out.println( n_membres );
                %>
              </h3> <!-- TODO : afficher le nombre de membres a la place de 12 -->
              <p>Membres</p>
            </div>
            <div class="icon">
            <ion-icon name="people"></ion-icon>
            </div>
            <a href="membre_list" class="small-box-footer" class="animsition-link">Liste des membres <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <div class="col l4 s6">
          <div class="small-box bg-green">
            <div class="inner">
              <h3><% 
                Object n_livres = request.getAttribute("livres_count");
                out.println( n_livres );
                %>
              </h3> <!-- TODO : afficher le nombre de livres a la place de 27 -->
              <p>Livres</p>
            </div>
            <div class="icon">
              <ion-icon name="book"></ion-icon>
            </div>
            <a href="livre_list" class="small-box-footer" class="animsition-link">Liste des livres <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <div class="col l4 s6">
          <div class="small-box bg-yellow">
            <div class="inner">
              <h3><% 
                Object n_emprunts = request.getAttribute("emprunts_count");
                out.println( n_emprunts );
                %>
              </h3> <!-- TODO : afficher le nombre d'emprunts a la place de 1515 -->
              <p>Emprunts</p>
            </div>
            <div class="icon">
              <ion-icon name="bookmarks"></ion-icon>
            </div>
            <a href="emprunt_list" class="small-box-footer" class="animsition-link">Liste des emprunts <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <div class="container">
	        <div class="col s12">
	          <h5>Emprunts en cours</h5>
	          <table class="striped">
                <thead>
                    <tr>
                        <th>Livre</th>
                        <th>Membre emprunteur</th>
                        <th>Date d'emprunt</th>
                        <th>Retour</th>
                    </tr>
                </thead>
                <tbody id="results">
                  <c:forEach items="${emprunts}" var="emprunt">
                      <tr>
                        <td><c:out value="${emprunt.getLivre().getTitre()}"></c:out>, <em><c:out value="${emprunt.getLivre().getAuteur()}"></c:out></em></td>
                        <td><c:out value="${emprunt.getMembre().getNom()}"></c:out> <c:out value="${emprunt.getMembre().getPrenom()}"></c:out></td>
                        <td><c:out value="${emprunt.getDateEmprunt()}"></c:out></td>
                        <td>
                            <a href="emprunt_return?id=idDeLEmprunt"><ion-icon class="table-item" name="log-in"></a>
                        </td>
                      </tr>
                    </c:forEach>
                
                     <!-- TODO : parcourir la liste des emprunts en cours et les afficher selon la structure d'exemple ci-dessus -->
                </tbody>
            </table>
          </div>
        </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>
