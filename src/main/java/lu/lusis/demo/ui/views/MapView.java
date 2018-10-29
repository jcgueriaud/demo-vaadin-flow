package lu.lusis.demo.ui.views;

import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import lu.lusis.demo.backend.data.Position;
import lu.lusis.demo.backend.service.PositionService;
import lu.lusis.demo.ui.AppLayout;
import lu.lusis.demo.utils.HasLogger;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.List;


@Tag("map-view")
@HtmlImport("src/views/map-view.html")
@Route(value = "map",layout = AppLayout.class)
public class MapView extends PolymerTemplate<MapView.MapModel> implements HasLogger {

    private final PositionService positionService;

    public static Double DISTANCE = 10.0; // 3 kms



    @Value("${googlemap.apikey}")
    private String googleMapApiKey ="sdds";

    public interface MapModel extends TemplateModel {
        void setPositions(List<Position> positions);
        void setApiKey(String apiKey);
    }


    public MapView(PositionService positionService) {
        this.positionService = positionService;
    }

    @PostConstruct
    private void init(){
        getModel().setApiKey(googleMapApiKey);
        updatePositions( 6.25, 49.6);
    }
    /*
    Récupère les coordonnées de la carte à la fin d'un drag sur celle-ci
    Les coordonnées sont recupérées par la variable event créee par l'event _onGoogleMapDragend dans home-view.html, grâce à @EventData.
    */
    @EventHandler
    private void _onGoogleMapDragend(@EventData("event.currentTarget.latitude") Double latitude, @EventData("event.currentTarget.longitude") Double longitude){
        updatePositions(longitude, latitude);
    }
    private void updatePositions(Double longitude, Double latitude){
        getLogger().debug("UPDATE position avec (long,lat) ("+ longitude+","+latitude+")");
       // getModel().setPositionList(positionService.findByDistance(longitude, latitude, DISTANCE));
        List<Position> positions = positionService.findByDistance(longitude, latitude, DISTANCE);
        if (positions != null)
            UI.getCurrent().getPage().setTitle(positions.size() + " position(s) trouvée(s)");
        else
            UI.getCurrent().getPage().setTitle( " Pas de position trouvée");
        getModel().setPositions(positions);
    }



}
