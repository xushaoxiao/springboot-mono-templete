package com.lightning.support.protobuf;

import com.fasterxml.jackson.core.util.VersionUtil;

/**
 * Helper class used for finding and caching version information for this module.
 */
class ModuleVersion extends VersionUtil {

  public static final ModuleVersion instance = new ModuleVersion();
}
