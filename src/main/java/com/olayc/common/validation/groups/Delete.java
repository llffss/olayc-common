package com.olayc.common.validation.groups;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, AsDelete.class})
public interface Delete {
}
