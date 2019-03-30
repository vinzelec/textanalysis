package fr.vinze.textanalysis.ws.business;

import fr.vinze.textanalysis.ws.dto.CorpusDTO;
import fr.vinze.textanalysis.ws.exception.WsException;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Response;

public class CorpusService {

    public void createCorpus(CorpusDTO corpus) throws WsException {
        // TODO
    }

    public CorpusDTO getCorpus( String name) throws WsException {
        // TODO
        return null;
    }

    public void updateCorpus(String name, CorpusDTO corpus) throws WsException {
        if (corpus.getName() != null && !StringUtils.equals(name, corpus.getName())) {
            throw new WsException(Response.Status.CONFLICT, "");
        }
        // TODO
    }

}
