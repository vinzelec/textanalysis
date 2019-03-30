package fr.vinze.textanalysis.ws;

import fr.vinze.textanalysis.ws.business.CorpusService;
import fr.vinze.textanalysis.ws.dto.CorpusDTO;
import fr.vinze.textanalysis.ws.exception.ExceptionToResponse;
import fr.vinze.textanalysis.ws.exception.WsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Path("/corpus")
@Consumes("application/json")
@Produces("application/json")
public class CorpusResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorpusResource.class);

    private CorpusService service = new CorpusService();

    @Context
    private UriInfo uriInfo;

    @POST
    public Response createCorpus(CorpusDTO corpus) {
        Response response = null;
        try {
            service.createCorpus(corpus);
            UriBuilder uriBuilder = UriBuilder.fromResource(CorpusResource.class).fragment(corpus.getName());
            response = Response.created(uriBuilder.build()).build();
        } catch (WsException e) {
            LOGGER.error("failed to create corpus", e);
            response = ExceptionToResponse.exceptionToResponse(e);
        }
        return response;
    }

    @GET
    @Path("{name}")
    public Response getCorpus(@PathParam("name") String name) {
        Response response = null;
        try {
            CorpusDTO corpus = service.getCorpus(name);
            response = Response.ok(corpus).build();
        } catch (WsException e) {
            LOGGER.error("failed to create corpus", e);
            response = ExceptionToResponse.exceptionToResponse(e);
        }
        return response;
    }

    @PUT
    @Path("{name}")
    public Response updateCorpus(@PathParam("name") String name, CorpusDTO corpus) {
        Response response = null;
        try {
            service.updateCorpus(name, corpus);
            response = Response.notModified().build();
        } catch (WsException e) {
            LOGGER.error("failed to create corpus", e);
            response = ExceptionToResponse.exceptionToResponse(e);
        }
        return response;
    }

}
