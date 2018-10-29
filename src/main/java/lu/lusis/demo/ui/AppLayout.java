package lu.lusis.demo.ui;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import de.kaesdingeling.hybridmenu.HybridMenu;
import de.kaesdingeling.hybridmenu.components.HMButton;
import de.kaesdingeling.hybridmenu.components.HMLabel;
import de.kaesdingeling.hybridmenu.components.LeftMenu;
import de.kaesdingeling.hybridmenu.components.TopMenu;
import de.kaesdingeling.hybridmenu.data.MenuConfig;
import de.kaesdingeling.hybridmenu.design.DesignItem;
import lu.lusis.demo.ui.views.MainView;
import lu.lusis.demo.ui.views.MapView;
import lu.lusis.demo.ui.views.UserListView;

@BodySize(height = "100vh", width = "100vw")
@Theme(Lumo.class)
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class AppLayout extends HybridMenu {



    @Override
    public boolean init(VaadinSession vaadinSession, UI ui) {
        withConfig(MenuConfig.get().withDesignItem(DesignItem.getWhiteDesign()).withBreadcrumbs(true));

        TopMenu topMenu = getTopMenu();
        getNotificationCenter()
                .setNotiButton(topMenu.add(HMButton.get()
                        .withDescription("Notifications")));

        LeftMenu leftMenu = getLeftMenu();

        leftMenu.add(HMLabel.get()
                .withCaption("<b>Demo App</b>"));


        getBreadCrumbs().setRoot(leftMenu.add(HMButton.get()
                .withCaption("Home")
                .withIcon(VaadinIcon.HOME)
                .withNavigateTo(MainView.class)));

        leftMenu.add(HMButton.get()
                .withCaption("User List")
                .withIcon(VaadinIcon.LIST)
                .withNavigateTo(UserListView.class));

        leftMenu.add(HMButton.get()
                .withCaption("Map")
                .withIcon(VaadinIcon.GLOBE)
                .withNavigateTo(MapView.class));

        return true; // build menu
    }



}
