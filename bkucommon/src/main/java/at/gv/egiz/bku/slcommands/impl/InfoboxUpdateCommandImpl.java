/*
* Copyright 2008 Federal Chancellery Austria and
* Graz University of Technology
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package at.gv.egiz.bku.slcommands.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.buergerkarte.namespaces.securitylayer._1.InfoboxUpdateRequestType;
import at.gv.egiz.bku.slcommands.InfoboxUpdateCommand;
import at.gv.egiz.bku.slcommands.SLCommandContext;
import at.gv.egiz.bku.slcommands.SLResult;
import at.gv.egiz.bku.slexceptions.SLCommandException;

public class InfoboxUpdateCommandImpl extends
    AbstractInfoboxCommandImpl<InfoboxUpdateRequestType> implements InfoboxUpdateCommand {
  
  private static Log log = LogFactory.getLog(InfoboxUpdateCommandImpl.class);

  @Override
  public String getName() {
    return "InfoboxUpdateRequest";
  }

  @Override
  protected String getInfoboxIdentifier(InfoboxUpdateRequestType request) {
    return request.getInfoboxIdentifier();
  }

  @Override
  public void init(SLCommandContext ctx, Object request) throws SLCommandException {
    super.init(ctx, request);
    
    InfoboxUpdateRequestType req = getRequestValue();
    
    if (req.getAssocArrayParameters() != null && 
        !(infobox instanceof AssocArrayInfobox)) {
      log.info("Got AssocArrayParameters but Infobox type is not AssocArray.");
      throw new SLCommandException(4010);
    }
    
    if (req.getBinaryFileParameters() != null &&
        !(infobox instanceof BinaryFileInfobox)) {
      log.info("Got BinaryFileParameters but Infobox type is not BinaryFile.");
      throw new SLCommandException(4010);
    }
    
  }

  @Override
  public SLResult execute() {
    
    try {
      return infobox.update(getRequestValue(), getCmdCtx());
    } catch (SLCommandException e) {
      return new ErrorResultImpl(e, getCmdCtx().getLocale());
    }
    
  }

}