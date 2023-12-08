package th.co.prior.training.file.component;

public class RestTemplateUtilsComponent {

    private static RestTemplateUtilsComponent restTemplateUtilsComponent;
    public static RestTemplateUtilsComponent getInstance() {
        if(null == restTemplateUtilsComponent){
            restTemplateUtilsComponent = new RestTemplateUtilsComponent();
        }
        return restTemplateUtilsComponent;
    }

}
