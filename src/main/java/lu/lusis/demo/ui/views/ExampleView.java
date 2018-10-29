package lu.lusis.demo.ui.views;

import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.router.Route;
import lu.lusis.demo.ui.AppLayout;
import lu.lusis.demo.ui.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Simple template example.
 */
@Tag("example-view")
@HtmlImport("src/example-view.html")
@Route(value = "example")
public class ExampleView extends PolymerTemplate<ExampleView.ExampleModel> {

    /**
     * Template model which defines the single "value" property.
     */
    public interface ExampleModel extends TemplateModel {

        void setValue(String name);
        String getValue();
    }

    public ExampleView() {
        // Set the initial value to the "value" property.
        getModel().setValue("TEST");
    }

    @EventHandler
    public void _onClick(){
        setValue(getModel().getValue() + " clic");
    }

    public void setValue(String value) {
        getModel().setValue(value);
    }
}
