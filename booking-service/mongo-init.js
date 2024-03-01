db.createUser(
    {
        user: "root",
        pwd: "pass",
        roles: [
            {
                role: "readWrite",
                db: "booking-service"
            }
        ]
    }
);