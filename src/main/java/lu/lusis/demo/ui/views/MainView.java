package lu.lusis.demo.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import lu.lusis.demo.ui.AppLayout;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lu.lusis.demo.ui.components.HelloWorld;

/**
 * Page d'accueil pour exliquer une utilisation simple:
 * - de composant HTML
 * - de composant custom
 * -
 */
@PageTitle("Accueil")
@Route(value = "",layout = AppLayout.class)
public class MainView extends VerticalLayout {

    public MainView() {
        /* Ajout d'un titre H1 */
        H1 h1 = new H1("Bienvenue dans l'application de démonstration");

        /* Utilisation d'un composant en API JAVA */
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setWho("Jean-Christophe");


        /* Création d'un bouton Vaadin */
        Button button = new Button("Click me",
                e -> Notification.show("Clicked"));


        /* Ajout des 3 composants créés à la layout "verticalLayout" */
        add(h1,helloWorld,button);
    }

}
