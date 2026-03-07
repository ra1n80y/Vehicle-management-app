# ===============================
# Seed Vehicle Data
# ===============================
$baseUrl = "http://localhost:8080/api/vehicles/register"
$headers = @{ "Content-Type" = "application/json" }

# Sample vehicles
$vehicles = @(
    @{ name="Toyota"; model="Corolla"; type="Sedan"; year=2022 },
    @{ name="Ford"; model="F-150"; type="Pickup"; year=2021 },
    @{ name="Honda"; model="Civic"; type="Sedan"; year=2023 },
    @{ name="Tesla"; model="Model 3"; type="Electric"; year=2023 },
    @{ name="Jeep"; model="Wrangler"; type="SUV"; year=2020 }
)

# Loop through each vehicle and post to API
foreach ($v in $vehicles) {
    $body = ($v | ConvertTo-Json)
    Write-Host "Creating vehicle: $($v.name) $($v.model)"
    Invoke-RestMethod -Method POST -Uri $baseUrl -Headers $headers -Body $body
}

Write-Host "`nAll sample vehicles created successfully!"
