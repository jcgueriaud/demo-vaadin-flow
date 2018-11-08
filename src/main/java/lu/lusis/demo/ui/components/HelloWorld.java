package lu.lusis.demo.ui.components;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 *
 * Exemple Hello world
 *
 */
@Tag("hello-world")
@HtmlImport("src/views/hello-world.html")
public class HelloWorld extends PolymerTemplate<HelloWorld.HelloWorldModel> {

    /**
     * Modèle du composant
     * Contient une propriété "Who"
     *
     */
    public interface HelloWorldModel extends TemplateModel {

        void setWho(String who);
        String getWho();
    }

    public HelloWorld() {
    }

    /**
     * Cette fonction est appelée lors du click sur le paragraphe
     * grâce à l'annotation EventHandler et son nom.
     * Voir _onClick dans le fichier src/views/hello-world.html
     *
     */
    @EventHandler
    private void _onClick(){
        Notification.show(getModel().getWho() + " says hello");
    }

    /**
     * Permet aux autres composants de modifier le modèle
     *
     * @param who Nom
     */
    public void setWho(String who) {
        getModel().setWho(who);
    }
}
