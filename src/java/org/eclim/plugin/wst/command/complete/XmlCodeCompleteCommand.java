/**
 * Copyright (c) 2005 - 2006
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclim.plugin.wst.command.complete;

import java.util.ArrayList;
import java.util.List;

import org.eclim.command.CommandLine;

import org.eclim.command.complete.AbstractCodeCompleteCommand;
import org.eclim.command.complete.CodeCompleteResult;

import org.eclim.eclipse.EclimPlugin;

import org.eclim.util.ProjectUtils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import org.eclipse.jface.text.ITextViewer;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import org.eclipse.wst.sse.core.StructuredModelManager;

import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

import org.eclipse.wst.xml.ui.internal.contentassist.XMLContentAssistProcessor;

/**
 * Command to handle xml code completion requests.
 *
 * @author Eric Van Dewoestine (ervandew@yahoo.com)
 * @version $Revision$
 */
public class XmlCodeCompleteCommand
  extends AbstractCodeCompleteCommand
{
  private static final List IGNORE = new ArrayList();
  static{
    IGNORE.add("comment - xml comment");
    IGNORE.add("XSL processing instruction - XSL processing instruction");
  }

  /**
   * {@inheritDoc}
   * @see AbstractCodeCompleteCommand#getContentAssistProcessor(CommandLine,String,String)
   */
  protected IContentAssistProcessor getContentAssistProcessor (
      CommandLine commandLine, String project, String file)
    throws Exception
  {
    return new XMLContentAssistProcessor();
  }

  /**
   * {@inheritDoc}
   * @see AbstractCodeCompleteCommand#getTextViewer(CommandLine,String,String)
   */
  protected ITextViewer getTextViewer (
      CommandLine commandLine, String project, String file)
    throws Exception
  {
    IFile ifile = ProjectUtils.getFile(ProjectUtils.getProject(project), file);
    ifile.refreshLocal(IResource.DEPTH_INFINITE, null);

    IStructuredModel model =
      StructuredModelManager.getModelManager().getModelForRead(ifile);

    StructuredTextViewer viewer =
      new StructuredTextViewer(EclimPlugin.getShell(), null, null, false, 0);
    viewer.setDocument(model.getStructuredDocument());
    return viewer;
  }

  /**
   * {@inheritDoc}
   * @see AbstractCodeCompleteCommand#acceptProposal(ICompletionProposal)
   */
  protected boolean acceptProposal (ICompletionProposal proposal)
  {
    String display = proposal.getDisplayString();
    return !display.toLowerCase().startsWith("close with") &&
      !IGNORE.contains(display);
  }

  /**
   * {@inheritDoc}
   * @see AbstractCodeCompleteCommand#getShortDescription(ICompletionProposal)
   */
  protected String getShortDescription (ICompletionProposal proposal)
  {
    String shortDesc = proposal.getAdditionalProposalInfo();
    if(shortDesc != null){
      int index = shortDesc.indexOf("</p>");
      if(index != -1){
        shortDesc = shortDesc.substring(index + 4);
        shortDesc = CodeCompleteResult.createShortDescription(shortDesc);
      }
    }
    return shortDesc;
  }
}
