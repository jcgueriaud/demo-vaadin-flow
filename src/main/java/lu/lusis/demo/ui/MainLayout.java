package lu.lusis.demo.ui;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lu.lusis.demo.ui.views.MainView;
import lu.lusis.demo.ui.views.UserListView;

/**
 * Exemple de layout très simple avec menu
 */
@BodySize(height = "100vh", width = "100vw")
@Theme(Lumo.class)
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainLayout extends VerticalLayout implements RouterLayout {

    public MainLayout(){
        RouterLink linkHome = new RouterLink(null, MainView.class);
        linkHome.add(VaadinIcon.HOME.create(), new Text("Home"));
        RouterLink linkUserList = new RouterLink(null, UserListView.class);
        linkUserList.add(VaadinIcon.LIST.create(), new Text("Liste d'utilisateurs"));

        add(new Div(linkHome,linkUserList));
        setSizeFull();
    }

}
