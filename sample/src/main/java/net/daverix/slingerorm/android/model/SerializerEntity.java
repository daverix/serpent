/*
 * Copyright 2015 David Laurell
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

package net.daverix.slingerorm.android.model;

import net.daverix.slingerorm.android.serialization.UuidSerializer;
import net.daverix.slingerorm.annotation.ColumnName;
import net.daverix.slingerorm.annotation.DatabaseEntity;
import net.daverix.slingerorm.annotation.PrimaryKey;
import net.daverix.slingerorm.annotation.Serializer;

import java.util.Date;
import java.util.UUID;

@DatabaseEntity
public class SerializerEntity {
    @Serializer(UuidSerializer.class) @PrimaryKey
    private UUID id;
    @ColumnName("_created")
    private Date created;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
