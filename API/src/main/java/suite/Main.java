package suite;

import static spark.Spark.port;

import controller.AppController;
import controller.ChannelController;
import controller.EpgController;
import controller.OrganizationController;
import controller.PaquetesController;
import controller.ParrillaController;
import controller.SuscriptorsController;
import controller.UserController;
import services.AppService;
import services.ChannelService;
import services.EpgService;
import services.OrganizationService;
import services.PaquetesService;
import services.ParrillaService;
import services.SuscriptorService;
import services.UserService;

public class Main {
    public static void main(String[] args) {
    	port(4567);
    	new UserController(new UserService());
    	new OrganizationController(new OrganizationService());
    	new ChannelController(new ChannelService());
    	new SuscriptorsController(new SuscriptorService());
    	new AppController(new AppService());
    	new EpgController(new EpgService());
    	new PaquetesController(new PaquetesService());
    	new ParrillaController(new ParrillaService());
    }
}

