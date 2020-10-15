package com.epam.gtc.dao.entities.constants;

import com.epam.gtc.dao.entities.UserEntity;

import java.util.Arrays;
import java.util.Objects;

/**
 * Roles
 *
 * @author Fazliddin Makhsudov
 */
public enum Role {
    ADMIN,
    MANAGER,
    USER,
    GUEST;

    /**
     * Get role.
     *
     * @param user
     * @return Role
     */
    public static Role getRole(final UserEntity user) {
        int roleId = user.getRoleId() - 1;
        return Role.values()[roleId];
    }

    /**
     * Get role.
     *
     * @param order
     * @return Role
     */
    public static Role getEnumFromId(final int order) {
        int roleId = order - 1;
        return Role.values()[roleId];
    }

    /**
     * Get name.
     *
     * @return String.
     */
    public String getName() {
        return name().toLowerCase();
    }

    /**
     * Get role order by name.
     *
     * @return int.
     */
    public static int getId(String name) {
        return Arrays.stream(Role.values())
                .filter(role -> role.name().equalsIgnoreCase(name))
                .findFirst().orElse(Role.USER).ordinal() + 1;
    }

    /**
     * Get role order by role.
     *
     * @return int.
     */
    public static int getId(Role roleB) {
        return Arrays.stream(Role.values())
                .filter(roleA -> Objects.equals(roleA, roleB))
                .findFirst().orElse(Role.USER).ordinal() + 1;
    }

    /**
     * Get role by name.
     *
     * @return int.
     */
    public static Role getEnumFromName(String name) {
        return Arrays.stream(Role.values())
                .filter(role -> role.name().equalsIgnoreCase(name))
                .findFirst().orElse(Role.USER);
    }

    @Override
    public String toString() {
        return this.name();
    }
}
