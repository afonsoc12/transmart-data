/*
 * Copyright © 2013-2014 The Hyve B.V.
 *
 * This file is part of transmart-data.
 *
 * Transmart-data is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * transmart-data.  If not, see <http://www.gnu.org/licenses/>.
 */

package inc.oracle

class GrantItem extends BasicItem {

    String permission

    GrantItem(Item grantedItem, permission, String grantee) {
        assert grantedItem != null
        assert grantee != null

        this.owner = grantee
        this.type = 'GRANT'
        this.name = "${grantedItem.owner}.${grantedItem.name}"
        this.permission = permission
    }

    @Override
    String getData() {
        "\nGRANT $permission ON $name TO $owner WITH GRANT OPTION;\n"
    }

    @Override
    boolean equals(Object obj) {
        obj.getClass() == GrantItem &&
                this.owner == obj.owner && this.name == obj.name &&
                this.type == obj.type && this.permission == obj.permission
    }

    int hashCode() {
        return type.hashCode() ^ name.hashCode() ^ owner.hashCode() ^
                permission.hashCode()
    }
}
