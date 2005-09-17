/**
 * Copyright (c) 2004 - 2005
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
package org.eclim.command;

import java.io.IOException;

import org.eclim.command.CommandLine;
import org.eclim.command.OutputFilter;

/**
 * Defines a command that can be exected.
 *
 * @author Eric Van Dewoestine (ervandew@yahoo.com)
 * @version $Revision$
 */
public interface Command
{
  /**
   * Executes the command with the supplied options.
   *
   * @param _commandLine The commandLine options supplied.
   * @return The result.
   */
  public Object execute (CommandLine _commandLine)
    throws IOException;

  /**
   * Gets an output filter by name.
   */
  public OutputFilter getFilter (String _name);
}
