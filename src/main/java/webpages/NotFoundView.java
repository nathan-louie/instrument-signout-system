package webpages;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

@Route(value = "notfound")
@Theme(value = Material.class, variant = Material.DARK)
@PageTitle("TuneLog | 404 Not Found")
public class NotFoundView extends VerticalLayout implements HasErrorParameter<NotFoundException>
{
	private final H1 error = new H1();

	public NotFoundView()
	{
		// Center the components in the view.
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		// Add the page not found message.
		add(error);
	}

	@Override
	public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter)
	{
		error.setText("Cannot find URL: " + event.getLocation().getPath());
		return HttpServletResponse.SC_NOT_FOUND;
	}
}
