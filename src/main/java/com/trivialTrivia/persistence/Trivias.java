package com.trivialTrivia.persistence;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trivialTrivia.api.TriviaApi;
import com.trivialTrivia.entity.Trivia;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The type Trivias.
 */
@Path("/trivia")
public class Trivias  {
    private Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Gets trivias.
     *
     * @param category   the category
     * @param amount     the amount
     * @param difficulty the difficulty
     * @param type       the type
     * @return the trivias
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/request")
    public Response getTrivias(@QueryParam("triviaCategory") @DefaultValue("") String category,
                               @QueryParam("triviaAmount") @DefaultValue("50") String amount,
                               @QueryParam("triviaDifficulty") @DefaultValue("") String difficulty,
                               @QueryParam("triviaType") @DefaultValue("") String type) {
        // instantiate trivia api
        TriviaApi triviaApi = new TriviaApi();
        // get trivia
        Trivia[] trivia = triviaApi.getTrivia(category, type, difficulty, amount);

        JSONObject response = new JSONObject();
        if (trivia.length > 0) {
            response.put("trivia", trivia);
        } else {
            response.put("error", "Error getting trivia questions, make sure request has params.");
        }
        return Response
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity(response)
                .build();
    }

}

