package lu.lusis.demo.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import lu.lusis.demo.ui.AppLayout;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lu.lusis.demo.ui.MainLayout;

@Route(value = "",layout = AppLayout.class)
public class MainView extends VerticalLayout {

    public MainView() {
        Button button = new Button("Click me",
                e -> Notification.show("Clicked"));
        add(button);
    }

}
