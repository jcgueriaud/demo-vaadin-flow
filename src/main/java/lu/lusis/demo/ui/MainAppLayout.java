package lu.lusis.demo.ui;

import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.appmenu.left.LeftNavigationComponent;
import com.github.appreciated.app.layout.component.appmenu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.design.AppLayoutDesign;
import com.github.appreciated.app.layout.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.notification.component.AppBarNotificationButton;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import lu.lusis.demo.ui.views.MainView;
import lu.lusis.demo.ui.views.MapView;
import lu.lusis.demo.ui.views.UserListView;


@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@HtmlImport("styles/shared-styles.html")
public class MainAppLayout extends AppLayoutRouterLayout {
    private Behaviour variant;
    private DefaultNotificationHolder notifications;

    @Override
    public com.github.appreciated.app.layout.behaviour.AppLayout getAppLayout() {
        if (variant == null) {
            variant = Behaviour.LEFT_HYBRID;
            notifications = new DefaultNotificationHolder(newStatus -> {
            });
        }
            return AppLayoutBuilder
                    .get(variant)
                    .withTitle("Application de démonstration")
                    .withAppBar(AppBarBuilder
                            .get()
                            .add(new AppBarNotificationButton(VaadinIcon.BELL, notifications))
                            .build())
                    .withDesign(AppLayoutDesign.MATERIAL)
                    .withAppMenu(LeftAppMenuBuilder
                            .get()
                            .add(new LeftNavigationComponent("Accueil", VaadinIcon.HOME.create(), MainView.class))
                            .add(new LeftNavigationComponent("Carte interactive", VaadinIcon.GLOBE.create(), MapView.class))
                            .add(new LeftNavigationComponent("Liste d'utilisateurs", VaadinIcon.TABLE.create(), UserListView.class))
                            .build())
                    .build();
    }
}