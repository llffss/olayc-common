package com.olayc.common.validation.groups;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, AsPost.class})
public interface Post {
}
